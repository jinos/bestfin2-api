package com.fin.best.bestfin.api.app.gateway.v1.product

import com.fin.best.bestfin.api.app.domain.product.service.ProductService
import com.fin.best.bestfin.api.app.gateway.v1.product.param.FinancialParams
import com.fin.best.bestfin.api.app.gateway.v1.product.param.ProductParams
import com.fin.best.bestfin.api.component.model.ResponseHandler
import com.fin.best.bestfin.api.component.constants.AppConst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(value = ["/api/v1/product"])
class ProductController
@Autowired constructor(
        private val productService: ProductService
) {
    /*
    * KB 상품 엑셀 업로드
    * */
    @RequestMapping(value = ["/kb-loan-product-excel-upload"], method = [RequestMethod.POST], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun kbLoanProductExcelUpload(
            @RequestPart file: MultipartFile
    ): ResponseHandler<*> {
        return ResponseHandler(statusCode = 200, responseBody = productService.kbLoanProductExcelUpload(file))
    }

    /*
    * 상품 검색
    * */
    @RequestMapping(value = [""], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fetchProducts(
            @RequestParam productType: AppConst.Product.LoanType?,
            @RequestParam useYn: AppConst.YN?,
            @RequestParam searchValue: String?,
            @RequestParam pageNo: Int?,
            @RequestParam itemsPerPage: Int?
    ): ResponseHandler<*> {
        val result = productService.fetchProducts(
                productType = productType,
                useYn = useYn,
                searchValue = searchValue,
                pageNo = pageNo?:1,
                itemsPerPage = itemsPerPage?:20
        )
        return ResponseHandler(statusCode = if (result.totalCount <= 0) 204 else 200, responseBody = result)
    }

    /*
    * 상품 등록
    * */
    @RequestMapping(value = [""], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun registProduct(
            @RequestBody registForm: ProductParams
    ): ResponseHandler<*> {
        // TODO admin no
        var adminNo = 1
        val result = productService.registProduct(registForm, 1)

        return if (result.success) ResponseHandler(statusCode = 200, responseBody = result.result)
        else ResponseHandler(statusCode = 400, resultCode = result.code!!, resultMessage = result.message!!, responseBody = null)
    }

    /*
    * 상품 상세
    * */
    @RequestMapping(value = ["/{productNo}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fetchProduct(
            @PathVariable("productNo") productNo: Long
    ): ResponseHandler<*> {
        val result = productService.fetchProduct(
                productNo = productNo
        )
        return ResponseHandler(statusCode = if (result == null) 204 else 200, responseBody = result)
    }

    /*
    * 금융사 로고 관리
    * */
    @RequestMapping(value = ["/profile"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fetchProductProfile(
            @RequestParam pageNo: Int?,
            @RequestParam itemsPerPage: Int?
    ): ResponseHandler<*> {
        val result = productService.fetchProductProfile(
                pageNo = pageNo?:1,
                itemsPerPage = itemsPerPage?:20
        )
        return ResponseHandler(statusCode = if (result.totalCount <= 0) 204 else 200, responseBody = result)
    }

    /*
    * 금융사 정보 수정
    * */
    @RequestMapping(value = ["/profile"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun modifyProduct(
            @RequestPart file: MultipartFile?,
            @RequestPart registForm: FinancialParams
    ): ResponseHandler<*> {
        // TODO admin no
        var adminNo = 1
        val result = productService.modifyProduct(file, registForm, 1)

        return if (result.success) ResponseHandler(statusCode = 200, responseBody = result.result)
        else ResponseHandler(statusCode = 400, resultCode = result.code!!, resultMessage = result.message!!, responseBody = null)
    }
}