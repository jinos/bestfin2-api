package com.fin.best.bestfin.api.app.domain.operation.servicve

import com.fin.best.bestfin.api.app.domain.common.dao.CommonDao
import com.fin.best.bestfin.api.app.domain.common.entity.CommonFiles
import com.fin.best.bestfin.api.app.domain.common.service.CommonService
import com.fin.best.bestfin.api.app.domain.product.dao.ProductDao
import com.fin.best.bestfin.api.app.domain.product.entity.KbProductPrice
import com.fin.best.bestfin.api.app.domain.product.entity.ProductBase
import com.fin.best.bestfin.api.app.domain.product.entity.ProductItem
import com.fin.best.bestfin.api.app.domain.product.entity.ProductOption
import com.fin.best.bestfin.api.app.domain.product.model.ProductBaseResult
import com.fin.best.bestfin.api.app.domain.product.model.ProductOptionResult
import com.fin.best.bestfin.api.app.domain.product.model.ProductResult
import com.fin.best.bestfin.api.app.domain.user.service.UserService
import com.fin.best.bestfin.api.app.gateway.v1.product.param.FinancialParams
import com.fin.best.bestfin.api.app.gateway.v1.product.param.OptionParams
import com.fin.best.bestfin.api.app.gateway.v1.product.param.ProductParams
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
class OperationService
@Autowired constructor(
        private val userService: UserService,
        private val productDao: ProductDao,
        private val commonDao: CommonDao,
        private val entityManager: EntityManager
) {
    private val logger: Logger = LoggerFactory.getLogger(OperationService::class.java)

    fun fetchAdminManagers(searchValue: String?, pageNo: Int, itemsPerPage: Int): PaginateResult {
        return PaginateResult.Builder()
                .page(pageNo, itemsPerPage)
                .totalCount(userService.countAdminManagers(searchValue = searchValue ?: ""))
                .build().pages(userService.fetchAdminManagers(
                        searchValue = searchValue ?: "",
                        pageNo = pageNo,
                        itemsPerPage = itemsPerPage
                ))
    }
}