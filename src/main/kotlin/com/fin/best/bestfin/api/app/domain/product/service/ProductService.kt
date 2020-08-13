package com.fin.best.bestfin.api.app.domain.product.service

import com.fin.best.bestfin.api.app.domain.common.dao.CommonDao
import com.fin.best.bestfin.api.app.domain.product.dao.ProductDao
import com.fin.best.bestfin.api.app.domain.product.entity.KbProductPrice
import com.fin.best.bestfin.api.app.domain.product.entity.ProductBase
import com.fin.best.bestfin.api.app.domain.product.entity.ProductItem
import com.fin.best.bestfin.api.app.domain.product.entity.ProductOption
import com.fin.best.bestfin.api.app.domain.product.model.ProductBaseResult
import com.fin.best.bestfin.api.app.domain.product.model.ProductOptionResult
import com.fin.best.bestfin.api.app.domain.product.model.ProductResult
import com.fin.best.bestfin.api.app.gateway.v1.product.param.OptionParams
import com.fin.best.bestfin.api.app.gateway.v1.product.param.ProductParams
import com.fin.best.bestfin.api.app.domain.common.entity.BankCode
import com.fin.best.bestfin.api.app.domain.common.entity.CommonFiles
import com.fin.best.bestfin.api.app.domain.common.service.CommonService
import com.fin.best.bestfin.api.app.gateway.v1.product.param.FinancialParams
import com.fin.best.bestfin.api.component.constants.AppConst
import com.fin.best.bestfin.api.component.constants.AppRegexp
import com.fin.best.bestfin.api.component.error.exception.ApiException
import com.fin.best.bestfin.api.component.error.exception.FailUploadFileHandleException
import com.fin.best.bestfin.api.component.error.exception.ParameterException
import com.fin.best.bestfin.api.component.error.exception.WrongExcelTemplateHandleException
import com.fin.best.bestfin.api.component.model.ConditionalResult
import com.fin.best.bestfin.api.component.model.FileVo
import com.fin.best.bestfin.api.component.paginate.PaginateResult
import com.fin.best.bestfin.api.component.utils.ExcelReadUtil
import com.fin.best.bestfin.api.component.utils.FileHandler
import com.fin.best.bestfin.api.component.utils.StringUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.math.BigInteger
import java.time.LocalDateTime
import java.util.*
import javax.persistence.EntityManager

@Service
class ProductService
@Autowired constructor(
        private val commonService: CommonService,
        private val productDao: ProductDao,
        private val commonDao: CommonDao,
        private val entityManager: EntityManager
) {
    private val logger: Logger = LoggerFactory.getLogger(ProductService::class.java)

    @Value("\${temporary-file-path}")
    lateinit var temporaryFilePath: String

    @Throws(WrongExcelTemplateHandleException::class)
    @Transactional
    fun kbLoanProductExcelUpload(file: MultipartFile): ConditionalResult {
        val fileId = StringUtil.generateTimeBaseUUID()
        val ext = file.originalFilename!!.substring(file.originalFilename!!.lastIndexOf(AppConst.Delimiters.Dot))
        val destPath = java.lang.String.join(AppConst.Delimiters.Slash, temporaryFilePath, java.lang.String.join("", fileId, ext))

        // Multipart file 을 로컬 저장소에 저장한다.
        if (!FileHandler.transferFiles(file, File(destPath))) {
            logger.error("Multipart File Transfer Failed {}", file)
            throw FailUploadFileHandleException()
        }

        // 저장된 file 의 정보를 불러온다.
        val fileChecked: Optional<File> = FileHandler.getFile(destPath)

        if (!fileChecked.isPresent) {
            logger.error("UnRead Files {}", file)
            throw FailUploadFileHandleException()
        }

        // file 에서 유효한 정보를 추출한뒤 이를 공통 파일 테이블에 저장한다.
        val fileVo: FileVo = FileHandler.generateFileOrigin(file.originalFilename!!, fileId, ext, fileChecked.get())
        val files: CommonFiles = commonService.storeCommonFiles(fileVo)

        if (files.fileNo == null) {
            logger.error("UnRead Files {}", file)
            throw FailUploadFileHandleException()
        }

        val excel = ExcelReadUtil()
        var list: List<KbProductPrice>? = null

        list = if (fileVo.mime == AppConst.File.XlsFileMimeType) {
            excel.xlsToCustomerVoList(destPath)
        } else {
            excel.xlsxToCustomerVoList(destPath)
        }

        if (list == null) {
            logger.error("Wrong Excel Template Format.")
            throw WrongExcelTemplateHandleException()
        }

        var idx = 0
        for (product in list) {
            val kbProductPrice = productDao.fetchKbProductPrice(product)
            if (kbProductPrice.isPresent) {
                product.issuedAt = kbProductPrice.get().issuedAt
                product.updatedAt = LocalDateTime.now()

                productDao.storeKbProductPrice(product)
            } else {
                productDao.persistKbProductPrices(entityManager, product)
            }

            idx++
            if (list.size == idx || idx % 300 == 0) {
                productDao.persistFlushKbProductPrices(entityManager)
            }
        }

        return ConditionalResult.Builder()
                .success(true)
                .build()
    }

    fun fetchProducts(productType: AppConst.Product.LoanType?, useYn: AppConst.YN?, searchValue: String?, pageNo: Int, itemsPerPage: Int): PaginateResult {
        val countQuery = ProductsServiceUtil.getCountQuery()
        val selectQuery = ProductsServiceUtil.getSelectQuery()
        var conditionQuery = ProductsServiceUtil.getConditionQuery()
        var orderingQuery = ProductsServiceUtil.getOrderingQuery()

        productType?.let {
            conditionQuery += " and pb.loan_type = '${productType}' "
        }

        useYn?.let {
            conditionQuery += " and pb.use_yn = '${useYn}' "
        }

        searchValue?.let {
            conditionQuery += " and (pb.kor_co_nm regexp '${searchValue}' or pb.fin_prdt_nm regexp '${searchValue}' "
        }

        // total count
        val totalQuery = entityManager.createNativeQuery(
                countQuery + conditionQuery)
        val totalCount = (totalQuery.singleResult as BigInteger).longValueExact()

        // result
        val nativeQuery = entityManager.createNativeQuery(
                selectQuery + conditionQuery + orderingQuery, "ProductBaseResultMapping")
        nativeQuery.firstResult = (pageNo - 1) * itemsPerPage
        nativeQuery.maxResults = itemsPerPage
        val result = nativeQuery.resultList as List<ProductBaseResult>

        return PaginateResult.Builder()
                .page(pageNo, itemsPerPage)
                .totalCount(totalCount)
                .build().pages(result.map {
                    val options = productDao.fetchProductOptionsBySeq(it.seq!!)
                    ProductResult(
                            base = productDao.fetchProductBase(it.seq!!),
                            options = if (options.isEmpty()) null
                            else options.map { option ->
                                ProductOptionResult(
                                        option = option,
                                        items = productDao.fetchProductItemBySeq(option.idx!!)
                                )
                            }
                    )
                })
    }

    @Transactional
    fun registProduct(registForm: ProductParams, adminNo: Long): ConditionalResult {
        val isEdit = registForm.productNo != null
        ProductsServiceUtil.checkValidParam(registForm)
        val finPrdtCd = StringUtil.generateTimeBaseUUID()
        val baseProduct = initializeProductBase(
                isEdit = isEdit,
                productNo = registForm.productNo,
                dclsMonth = registForm.dclsMonth ?: "",
                finCoNo = registForm.finCoNo ?: "",
                korCoNm = registForm.korCoNm ?: "",
                finPrdtCd = finPrdtCd,
                finPrdtNm = registForm.finPrdtNm ?: "",
                description = registForm.description ?: "",
                weight = registForm.weight ?: "20",

                loanLmt = registForm.loanLmt ?: "",
                joinWay = registForm.joinWay ?: "",
                joinWayStatus = registForm.joinWayStatus ?: AppConst.YN.N,
                loanInciExpn = registForm.loanInciExpn ?: "",
                loanInciExpnStatus = registForm.loanInciExpnStatus ?: AppConst.YN.N,
                erlyRpayFee = registForm.erlyRpayFee ?: "",
                erlyRpayFeeStatus = registForm.erlyRpayFeeStatus ?: AppConst.YN.N,
                dlyRate = registForm.dlyRate ?: "",
                dlyRateStatus = registForm.dlyRateStatus ?: AppConst.YN.N,
                dclsStrtDay = registForm.dclsStrtDay ?: "",
                loanLmtStatus = registForm.loanLmtStatus ?: AppConst.YN.N,
                dclsEndDay = registForm.dclsEndDay ?: "",
                finCoSubmDay = registForm.finCoSubmDay ?: "",

                mortgageType = registForm.mortgageType ?: AppConst.Product.MortgageType.NONE,
                cbName = registForm.cbName ?: "",
                crdtPrdtType = "",
                crdtPrdtTypeNm = registForm.crdtPrdtTypeNm ?: "",
                webUrl = registForm.webUrl ?: "",
                bankType = registForm.bankType ?: AppConst.YN.N,
                carCondition = registForm.carCondition ?: "",
                lendRateMin = registForm.lendRateMin ?: "",
                lendRateMax = registForm.lendRateMax ?: "",
                guaranteeType = registForm.guaranteeType ?: AppConst.Product.GuaranteeType.NONE,
                loanType = registForm.loanType,
                createUserNo = adminNo,
                useYn = registForm.useYn ?: AppConst.YN.N
        )

        if (baseProduct == null) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
            return ConditionalResult.Builder()
                    .success(false)
                    .reason(code = 400, message = "Failed Created Product Base")
                    .build()
        }

        val optionProduct = initializeProductOptions(
                isEdit = isEdit,
                productBase = baseProduct,
                dclsMonth = registForm.dclsMonth ?: "",
                finCoNo = registForm.finCoNo ?: "",
                finPrdtCd = finPrdtCd,
                crdtPrdtType = "",
                options = registForm.options,
                loanType = registForm.loanType,
                createUserNo = adminNo
        )

        if (!optionProduct.success) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
            return ConditionalResult.Builder()
                    .success(false)
                    .reason(411, "Unhandled Exception")
                    .build()
        } else
            return ConditionalResult.Builder()
                    .success(true)
                    .build()
    }

    @Throws(ApiException::class)
    private fun initializeProductBase(
            isEdit: Boolean,
            productNo: String?,
            dclsMonth: String,
            finCoNo: String,
            korCoNm: String,
            finPrdtCd: String,
            finPrdtNm: String,
            joinWay: String,
            joinWayStatus: AppConst.YN,
            loanInciExpn: String,
            loanInciExpnStatus: AppConst.YN,
            erlyRpayFee: String,
            erlyRpayFeeStatus: AppConst.YN,
            dlyRate: String,
            dlyRateStatus: AppConst.YN,
            loanLmt: String,
            loanLmtStatus: AppConst.YN,
            dclsStrtDay: String,
            dclsEndDay: String,
            finCoSubmDay: String,
            crdtPrdtType: String,
            cbName: String,
            crdtPrdtTypeNm: String,
            loanType: AppConst.Product.LoanType,
            webUrl: String,
            bankType: AppConst.YN,
            description: String,
            carCondition: String,
            lendRateMin: String,
            lendRateMax: String,
            weight: String,
            mortgageType: AppConst.Product.MortgageType,
            guaranteeType: AppConst.Product.GuaranteeType,
            createUserNo: Long,
            useYn: AppConst.YN
    ): ProductBase {
        if (isEdit) {
            val productBase = productDao.fetchProductBase(productNo!!.toLong())
            if (productBase == null)
                throw ApiException(400, "no exist product")
            else {
                productBase.description = description
                productBase.joinWay = joinWay
                productBase.joinWayStatus = joinWayStatus
                productBase.loanInciExpn = loanInciExpn
                productBase.loanInciExpnStatus = loanInciExpnStatus
                productBase.erlyRpayFee = erlyRpayFee
                productBase.erlyRpayFeeStatus = erlyRpayFeeStatus
                productBase.dlyRate = dlyRate
                productBase.dlyRateStatus = dlyRateStatus
                productBase.loanLmt = loanLmt
                productBase.loanLmtStatus = loanLmtStatus
                productBase.weight = weight.toInt()

                productBase.webUrl = webUrl

                productBase.loanLmt = loanLmt
                productBase.carCondition = carCondition
                productBase.lendRateMin = lendRateMin
                productBase.lendRateMax = lendRateMax

                productBase.useYn = useYn
                productBase.updateUserNo = createUserNo
                productBase.updateAt = LocalDateTime.now()

                return productDao.storeProductBase(productBase)
            }
        } else {
            return productDao.storeProductBase(ProductBase(
                    dclsMonth = dclsMonth,
                    finCoNo = finCoNo,
                    korCoNm = korCoNm,
                    finPrdtCd = finPrdtCd,
                    finPrdtNm = finPrdtNm,
                    joinWay = joinWay,
                    joinWayStatus = joinWayStatus ?: AppConst.YN.N,
                    loanInciExpn = loanInciExpn,
                    loanInciExpnStatus = loanInciExpnStatus ?: AppConst.YN.N,
                    erlyRpayFee = erlyRpayFee,
                    erlyRpayFeeStatus = erlyRpayFeeStatus ?: AppConst.YN.N,
                    dlyRate = dlyRate,
                    dlyRateStatus = dlyRateStatus ?: AppConst.YN.N,
                    loanLmt = loanLmt,
                    loanLmtStatus = loanLmtStatus ?: AppConst.YN.N,
                    dclsStrtDay = dclsStrtDay,
                    dclsEndDay = dclsEndDay,
                    finCoSubmDay = finCoSubmDay,
                    crdtPrdtType = crdtPrdtType,
                    cbName = cbName,
                    crdtPrdtTypeNm = crdtPrdtTypeNm,
                    loanType = loanType,
                    webUrl = webUrl,
                    bankType = bankType,
                    description = description,
                    carCondition = carCondition,
                    lendRateMin = lendRateMin,
                    lendRateMax = lendRateMax,
                    weight = weight.toInt(),
                    mortgageType = mortgageType,
                    guaranteeType = guaranteeType,
                    issueAt = LocalDateTime.now(),
                    createUserNo = createUserNo,
                    useYn = useYn
            ))
        }
    }

    private fun initializeProductOptions(isEdit: Boolean, productBase: ProductBase, dclsMonth: String, finCoNo: String, finPrdtCd: String, crdtPrdtType: String, options: List<OptionParams>?, loanType: AppConst.Product.LoanType, createUserNo: Long): ConditionalResult {
        when (loanType) {
            AppConst.Product.LoanType.M, AppConst.Product.LoanType.R, AppConst.Product.LoanType.C -> {
                options?.let {
                    it.forEach { option ->
                        if (initializeProductOptionData(
                                        isEdit = isEdit,
                                        idx = option.idx,
                                        productBase = productBase,
                                        dclsMonth = dclsMonth,
                                        finCoNo = finCoNo,
                                        finPrdtCd = finPrdtCd,
                                        crdtPrdtType = "",
                                        loanType = loanType,
                                        option = option,
                                        createUserNo = createUserNo
                                ) == null) {
                            return ConditionalResult.Builder()
                                    .success(false)
                                    .reason(code = 400, message = "No Input Product Option Data")
                                    .build()
                        }
                    }
                } ?: return ConditionalResult.Builder()
                        .success(false)
                        .reason(code = 400, message = "No Input Product Option Data")
                        .build()
            }
            else -> {
                return ConditionalResult.Builder()
                        .success(true)
                        .build()
            }
        }

        return return ConditionalResult.Builder()
                .success(true)
                .build()
    }

    private fun initializeProductOptionData(isEdit: Boolean, idx: String?, productBase: ProductBase, dclsMonth: String, finCoNo: String, finPrdtCd: String, crdtPrdtType: String, loanType: AppConst.Product.LoanType, option: OptionParams, createUserNo: Long): ProductOption? {
        var productOption: ProductOption? = null
        if (isEdit) {
            idx ?: throw ParameterException(400, "no input Product Option idx")
            productOption = productDao.fetchProductOption(idx.toLong())
                    ?: throw ApiException(404, "not exist Product Option")

            productOption.lendRateMin = option.lendRateMin ?: ""
            productOption.lendRateMinStatus = option.lendRateMinStatus ?: AppConst.YN.N
            productOption.lendRateMax = option.lendRateMax ?: ""
            productOption.lendRateMaxStatus = option.lendRateMaxStatus ?: AppConst.YN.N
            productOption.lendRateAvg = option.lendRateAvg ?: ""
            productOption.lendRateAvgStatus = option.lendRateAvgStatus ?: AppConst.YN.N

            productOption.crdtGrad1 = option.crdtGrad1 ?: ""
            productOption.crdtGrad1Status = option.crdtGrad1Status ?: AppConst.YN.N
            productOption.crdtGrad4 = option.crdtGrad4 ?: ""
            productOption.crdtGrad4Status = option.crdtGrad4Status ?: AppConst.YN.N
            productOption.crdtGrad5 = option.crdtGrad5 ?: ""
            productOption.crdtGrad5Status = option.crdtGrad5Status ?: AppConst.YN.N
            productOption.crdtGrad6 = option.crdtGrad6 ?: ""
            productOption.crdtGrad6Status = option.crdtGrad6Status ?: AppConst.YN.N
            productOption.crdtGrad10 = option.crdtGrad10 ?: ""
            productOption.crdtGrad10Status = option.crdtGrad10Status ?: AppConst.YN.N
            productOption.crdtGradAvg = option.crdtGradAvg ?: ""
            productOption.crdtGradAvgStatus = option.crdtGradAvgStatus ?: AppConst.YN.N

            productOption.weight = option.weight?.let { it.toInt() } ?: 20

            productOption.useYn = option.useYn ?: AppConst.YN.N
            productOption.updateUserNo = createUserNo
            productOption.updateAt = LocalDateTime.now()

            productOption = productDao.storeProductOption(productOption)
        } else {
            productOption = productDao.storeProductOption(ProductOption(
                    seq = productBase.seq!!,
                    dclsMonth = dclsMonth,
                    finCoNo = finCoNo,
                    finPrdtCd = finPrdtCd,
                    mrtgType = option.mrtgType ?: AppConst.Product.MrtgType.N,
                    mrtgTypeNm = option.mrtgTypeNm ?: "",
                    rpayType = option.rpayType ?: AppConst.Product.RpayType.N,
                    rpayTypeNm = option.rpayTypeNm ?: "",
                    lendRateType = option.lendRateType ?: AppConst.Product.LendRateType.N,
                    lendRateTypeNm = option.lendRateTypeNm ?: "",
                    lendRateMin = option.lendRateMin ?: "",
                    lendRateMinStatus = option.lendRateMinStatus ?: AppConst.YN.N,
                    lendRateMax = option.lendRateMax ?: "",
                    lendRateMaxStatus = option.lendRateMaxStatus ?: AppConst.YN.N,
                    lendRateAvg = option.lendRateAvg ?: "",
                    lendRateAvgStatus = option.lendRateAvgStatus ?: AppConst.YN.N,
                    crdtPrdtType = crdtPrdtType,
                    crdtLendRateType = option.crdtLendRateType ?: AppConst.Product.CrdtLendRateType.N,
                    crdtLendRateTypeNm = option.crdtLendRateTypeNm ?: "",
                    crdtGrad1 = option.crdtGrad1 ?: "",
                    crdtGrad1Status = option.crdtGrad1Status ?: AppConst.YN.N,
                    crdtGrad4 = option.crdtGrad4 ?: "",
                    crdtGrad4Status = option.crdtGrad4Status ?: AppConst.YN.N,
                    crdtGrad5 = option.crdtGrad5 ?: "",
                    crdtGrad5Status = option.crdtGrad5Status ?: AppConst.YN.N,
                    crdtGrad6 = option.crdtGrad6 ?: "",
                    crdtGrad6Status = option.crdtGrad6Status ?: AppConst.YN.N,
                    crdtGrad10 = option.crdtGrad10 ?: "",
                    crdtGrad10Status = option.crdtGrad10Status ?: AppConst.YN.N,
                    crdtGradAvg = option.crdtGradAvg ?: "",
                    crdtGradAvgStatus = option.crdtGradAvgStatus ?: AppConst.YN.N,
                    weight = option.weight?.let { it.toInt() } ?: 20,
                    loanType = loanType,
                    issueAt = LocalDateTime.now(),
                    createUserNo = createUserNo
            ))
        }

        if (productOption != null) {
            when (loanType) {
                AppConst.Product.LoanType.M, AppConst.Product.LoanType.R -> {
                    option.preference?.let {
                        it.forEach { item ->
                            if (isEdit) {
                                idx ?: throw ParameterException(400, "no input Product Item idx")
                                val productItem = productDao.fetchProductItem(idx.toLong())
                                        ?: throw ApiException(404, "not exist Product Item")

                                productItem.title = item.title!!
                                productItem.rate = item.rate!!.toFloat()
                                productItem.useYn = item.useYn ?: AppConst.YN.N

                            } else {
                                productDao.storeProductItem(ProductItem(
                                        seq = productOption.idx!!,
                                        title = item.title!!,
                                        rate = item.rate!!.toFloat(),
                                        createUserNo = createUserNo,
                                        issueAt = LocalDateTime.now()
                                ))
                            }
                        }
                    } ?: return null
                }
                else -> {
                }
            }
        }

        return productOption
    }

    fun fetchProduct(productNo: Long): ProductResult? {
        val product = productDao.fetchProductBase(productNo)
        return if (product == null) null
        else {
            val options = productDao.fetchProductOptionsBySeq(productNo)
            ProductResult(
                    base = productDao.fetchProductBase(productNo),
                    options = if (options.isEmpty()) null
                    else options.map { option ->
                        ProductOptionResult(
                                option = option,
                                items = productDao.fetchProductItemBySeq(option.idx!!)
                        )
                    }
            )
        }
    }

    fun fetchProductProfile(pageNo: Int, itemsPerPage: Int): PaginateResult {
        return PaginateResult.Builder()
                .page(pageNo, itemsPerPage)
                .totalCount(commonDao.fetchProductProfileCount())
                .build().pages(commonDao.fetchProductProfile(
                        pageNo = pageNo,
                        itemsPerPage = itemsPerPage
                ))
    }

    fun modifyProduct(file: MultipartFile?, registForm: FinancialParams, adminNo: Long): ConditionalResult {
        var files: CommonFiles? = null
        if (file != null) {
            val fileId = StringUtil.generateTimeBaseUUID()
            val ext = file.originalFilename!!.substring(file.originalFilename!!.lastIndexOf(AppConst.Delimiters.Dot))
            val destPath = java.lang.String.join(AppConst.Delimiters.Slash, temporaryFilePath, java.lang.String.join("", fileId, ext))

            // Multipart file 을 로컬 저장소에 저장한다.
            if (!FileHandler.transferFiles(file, File(destPath))) {
                logger.error("Multipart File Transfer Failed {}", file)
                throw FailUploadFileHandleException()
            }

            // 저장된 file 의 정보를 불러온다.
            val fileChecked: Optional<File> = FileHandler.getFile(destPath)

            if (!fileChecked.isPresent) {
                logger.error("UnRead Files {}", file)
                throw FailUploadFileHandleException()
            }

            // file 에서 유효한 정보를 추출한뒤 이를 공통 파일 테이블에 저장한다.
            val fileVo: FileVo = FileHandler.generateFileOrigin(file.originalFilename!!, fileId, ext, fileChecked.get())
            files = commonService.storeCommonFiles(fileVo)
        }

        val bankCode = commonDao.fetchBankCode(registForm.finCoNo.toLong())
                ?: return ConditionalResult.Builder()
                        .success(false)
                        .reason(400, "No Exist Bank Information.")
                        .build()

        files?.let { bankCode.fileNo = it.fileNo }
        registForm.aliasKorCoNm?.let {bankCode.aliasKorCoNm = it}
        bankCode.updateUserNo = adminNo
        bankCode.updateAt = LocalDateTime.now()

        commonDao.storeBankCode(bankCode)

        return ConditionalResult.Builder()
                .success(true)
                .result(bankCode)
                .build()
    }
}


object ProductsServiceUtil {
    fun checkValidParam(registForm: ProductParams) {
        when (registForm.loanType) {
            AppConst.Product.LoanType.M, AppConst.Product.LoanType.R, AppConst.Product.LoanType.C -> {
                registForm.finCoNo ?: throw ParameterException(400, "금융회사가 누락되었습니다.")
                registForm.korCoNm ?: throw ParameterException(400, "금융회사가 누락되었습니다.")
                registForm.finPrdtNm ?: throw ParameterException(400, "금융상품명이 누락되었습니다.")
                registForm.description ?: throw ParameterException(400, "상품설명이 누락되었습니다.")
                if (registForm.loanType == AppConst.Product.LoanType.C) {
                    registForm.webUrl ?: throw ParameterException(400, "웹 URL 주소가 누락되었습니다.")
                    if (!registForm.webUrl.matches(AppRegexp.Common.Url.toRegex())) throw ParameterException(400, "웹 URL 주소 형식이 틀립니다.")
                }
                registForm.options ?: throw ParameterException(400, "하위상품이 누락되었습니다.")

                registForm.options!!.forEach { option ->
                    if (registForm.loanType == AppConst.Product.LoanType.M || registForm.loanType == AppConst.Product.LoanType.R) {
                        if (registForm.loanType == AppConst.Product.LoanType.M)
                            option.mrtgType ?: throw ParameterException(400, "담보 유형이 누락되었습니다.")
                        option.rpayType ?: throw ParameterException(400, "대출 상환유형이 누락되었습니다.")
                        option.lendRateType ?: throw ParameterException(400, "대출 금리유형이 누락되었습니다.")
                        option.lendRateMin ?: throw ParameterException(400, "대출금리_최저 항목이 누락되었습니다.")
                        if (!option.lendRateMin.matches(AppRegexp.Common.Decimal.toRegex())) throw ParameterException(400, "대출금리_최저 항목의 값이 잘못되었습니다..")
                        option.lendRateMax ?: throw ParameterException(400, "대출금리_최고 항목이 누락되었습니다.")
                        if (!option.lendRateMax.matches(AppRegexp.Common.Decimal.toRegex())) throw ParameterException(400, "대출금리_최고 항목의 값이 잘못되었습니다..")
                        option.lendRateAvg ?: throw ParameterException(400, "전월 취급 평균금리 항목이 누락되었습니다.")
                        if (!option.lendRateAvg.matches(AppRegexp.Common.Decimal.toRegex())) throw ParameterException(400, "전월 취급 평균금리 항목의 값이 잘못되었습니다..")

                        option.preference ?: throw ParameterException(400, "우대 항목이 누락되었습니다.")
                        option.preference!!.forEach { item ->
                            item.title ?: throw ParameterException(400, "우대 항목명이 누락되었습니다.")
                            item.rate ?: throw ParameterException(400, "우대 항목 금리가 누락되었습니다.")
                            if (!item.rate.matches(AppRegexp.Common.Decimal.toRegex())) throw ParameterException(400, "우대 항목 금리의 값이 잘못되었습니다..")
                        }
                    } else {
                        option.crdtLendRateType ?: throw ParameterException(400, "금리구분 값이 누락되었습니다.")
                        option.crdtGrad1 ?: throw ParameterException(400, "은행: 1~2 등급, 비은행 1~3등급 값이 누락되었습니다.")
                        if (!option.crdtGrad1.matches(AppRegexp.Common.Decimal.toRegex())) throw ParameterException(400, "우대 항목 금리의 값이 잘못되었습니다..")
                        option.crdtGrad4 ?: throw ParameterException(400, "은행: 3~4 등급, 비은행 4 등급 값이 누락되었습니다.")
                        if (!option.crdtGrad4.matches(AppRegexp.Common.Decimal.toRegex())) throw ParameterException(400, "우대 항목 금리의 값이 잘못되었습니다..")
                        option.crdtGrad5 ?: throw ParameterException(400, "은행: 5~6 등급, 비은행 5 등급 값이 누락되었습니다.")
                        if (!option.crdtGrad5.matches(AppRegexp.Common.Decimal.toRegex())) throw ParameterException(400, "우대 항목 금리의 값이 잘못되었습니다..")
                        option.crdtGrad6 ?: throw ParameterException(400, "은행: 7~8 등급, 비은행 6 등급 값이 누락되었습니다.")
                        if (!option.crdtGrad6.matches(AppRegexp.Common.Decimal.toRegex())) throw ParameterException(400, "우대 항목 금리의 값이 잘못되었습니다..")
                        option.crdtGrad10 ?: throw ParameterException(400, "은행: 9~10 등급, 비은행 7~10 등급 값이 누락되었습니다.")
                        if (!option.crdtGrad10.matches(AppRegexp.Common.Decimal.toRegex())) throw ParameterException(400, "우대 항목 금리의 값이 잘못되었습니다..")
                        option.crdtGradAvg ?: throw ParameterException(400, "평균 금리 값이 누락되었습니다.")
                        if (!option.crdtGradAvg.matches(AppRegexp.Common.Decimal.toRegex())) throw ParameterException(400, "우대 항목 금리의 값이 잘못되었습니다..")
                    }
                }
            }
            AppConst.Product.LoanType.V -> {
                if (!StringUtils.isEmpty(registForm.lendRateMin) && !registForm.lendRateMin!!.matches(AppRegexp.Common.Decimal.toRegex())) throw ParameterException(400, "최저 금리의 값이 잘못되었습니다..")
                if (!StringUtils.isEmpty(registForm.lendRateMax) && !registForm.lendRateMax!!.matches(AppRegexp.Common.Decimal.toRegex())) throw ParameterException(400, "최고 금리의 값이 잘못되었습니다..")
                //registForm.webUrl ?: throw ParameterException(400, "웹 URL 주소가 누락되었습니다.")

                if (!StringUtils.isEmpty(registForm.webUrl) && !registForm.webUrl!!.matches(AppRegexp.Common.Url.toRegex())) throw ParameterException(400, "웹 URL 주소 형식이 틀립니다.")
            }
        }
    }

    fun getCountQuery(): String {
        return """
        select 
            count(1)
        from product_base as pb 
        """.trimIndent()
    }

    fun getSelectQuery(): String {
        return """
        select 
             seq
            ,fin_co_no as finCoNo
            ,kor_co_nm as korCoNm
            ,fin_prdt_cd as finPrdtCd
            ,fin_prdt_nm as finPrdtNm
            ,loan_type as loanType
            ,dcls_month as dclsMonth
            ,use_yn as useYn
        from product_base as pb
        """.trimIndent()
    }

    fun getConditionQuery(): String {
        return " where 1=1 "
    }

    fun getOrderingQuery(): String {
        return " order by pb.seq desc "
    }
}
