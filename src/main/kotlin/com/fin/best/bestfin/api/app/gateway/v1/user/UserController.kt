package com.fin.best.bestfin.api.app.gateway.v1.user

import com.fin.best.bestfin.api.app.domain.user.service.UserService
import com.fin.best.bestfin.api.component.model.ResponseHandler
import com.fin.best.bestfin.api.app.gateway.v1.user.param.UserCheckMobile
import com.fin.best.bestfin.api.app.gateway.v1.user.param.UserModifyChangeEmail
import com.fin.best.bestfin.api.app.gateway.v1.user.param.UserModifyChangeMobile
import com.fin.best.bestfin.api.app.gateway.v1.user.param.UserModifyChangePassword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/api/v1/users"])
class UserController
@Autowired constructor(
        private val userService: UserService
) {
    @RequestMapping(value = ["/{userNo}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fetchUser(
            @PathVariable("userNo") userNo: String
    ): ResponseHandler<*> {
        return ResponseHandler(statusCode = 200, responseBody = userService.fetchUser(userNo.toLong()))
    }

    @RequestMapping(value = ["/{userNo}/change/eamil"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun changeUserEamil(
            @PathVariable("userNo") userNo: String,
            @RequestBody @Valid params: UserModifyChangeEmail
    ): ResponseHandler<*> {
        return ResponseHandler(statusCode = 200, responseBody = userService.changeUserEamil(userNo.toLong(), params))
    }

    @RequestMapping(value = ["/{userNo}/change/password"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun changeUserPassword(
            @PathVariable("userNo") userNo: String,
            @RequestBody @Valid params: UserModifyChangePassword
    ): ResponseHandler<*> {
        return ResponseHandler(statusCode = 200, responseBody = userService.changeUserPassword(userNo.toLong(), params))
    }

    @RequestMapping(value = ["/{userNo}/change/mobile"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun changeUserMobile(
            @PathVariable("userNo") userNo: String,
            @RequestBody @Valid params: UserModifyChangeMobile
    ): ResponseHandler<*> {
        return ResponseHandler(statusCode = 200, responseBody = userService.changeUserMobile(userNo.toLong(), params))
    }

    @RequestMapping(value = ["/{userNo}/check/password"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun checkUserPassword(
            @PathVariable("userNo") userNo: String,
            @RequestBody @Valid params: UserCheckMobile
    ): ResponseHandler<*> {
        return ResponseHandler(statusCode = 200, responseBody = userService.checkUserPassword(userNo.toLong(), params))
    }

    @RequestMapping(value = ["/{userNo}/withdraw"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun withdrawUser(
            @PathVariable("userNo") userNo: String
    ): ResponseHandler<*> {
        return ResponseHandler(statusCode = 200, responseBody = userService.withdrawUser(userNo.toLong()))
    }
}