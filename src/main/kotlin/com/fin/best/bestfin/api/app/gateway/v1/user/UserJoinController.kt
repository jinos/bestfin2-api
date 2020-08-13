package com.fin.best.bestfin.api.app.gateway.v1.user

import com.fin.best.bestfin.api.app.domain.user.service.UserJoinService
import com.fin.best.bestfin.api.component.model.ResponseHandler
import com.fin.best.bestfin.api.app.gateway.v1.user.param.UserSignUpParams
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/api/v1/join"])
class UserJoinController
@Autowired constructor(
        private val userJoinService: UserJoinService
) {
    @RequestMapping(value = ["/id"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun joinId(
            @RequestBody @Valid params: UserSignUpParams
    ): ResponseHandler<*> {
        return ResponseHandler(statusCode = 200, responseBody = userJoinService.signUpId(params))
    }

    @RequestMapping(value = ["/3rd"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun join3rd(
            @RequestBody @Valid params: UserSignUpParams
    ): ResponseHandler<*> {
        return ResponseHandler(statusCode = 200, responseBody = userJoinService.signUp3rd(params))
    }
}