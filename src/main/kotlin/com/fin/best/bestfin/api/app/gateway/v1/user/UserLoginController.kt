package com.fin.best.bestfin.api.app.gateway.v1.user

import com.fin.best.bestfin.api.app.domain.user.service.UserLoginService
import com.fin.best.bestfin.api.app.gateway.v1.user.param.UserSignInParams
import com.fin.best.bestfin.api.component.model.ResponseHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/api/v1/login"])
class UserLoginController
@Autowired constructor(
        private val userLoginService: UserLoginService
) {
    @RequestMapping(value = ["/user"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun loginUser(
            @RequestBody @Valid params: UserSignInParams
    ): ResponseHandler<*> {
        return ResponseHandler(statusCode = 200, responseBody = userLoginService.loginUser(params))
    }
}