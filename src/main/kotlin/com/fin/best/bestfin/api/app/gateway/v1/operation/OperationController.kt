package com.fin.best.bestfin.api.app.gateway.v1.operation

import com.fin.best.bestfin.api.app.domain.operation.servicve.OperationService
import com.fin.best.bestfin.api.component.model.ResponseHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/v1/operation"])
class OperationController
@Autowired constructor(
        private val operationService: OperationService
) {
    /*
    * 관리자 목록
    * */
    @RequestMapping(value = ["/admin-managers"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fetchAdminManagers(
            @RequestParam searchValue: String?,
            @RequestParam pageNo: Int?,
            @RequestParam itemsPerPage: Int?
    ): ResponseHandler<*> {
        val result = operationService.fetchAdminManagers(
                searchValue = searchValue,
                pageNo = pageNo?:1,
                itemsPerPage = itemsPerPage?:20
        )
        return ResponseHandler(statusCode = if (result.totalCount <= 0) 204 else 200, responseBody = result)
    }

}