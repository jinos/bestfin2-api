package com.fin.best.bestfin.api.app.gateway.v1.user.param

import com.fin.best.bestfin.api.component.constants.AppConst
import com.fin.best.bestfin.api.component.constants.AppRegexp
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class UserModifyChangeEmail(
        @get:Pattern(regexp = AppRegexp.User.Email)
        val email: String
)

data class UserModifyChangePassword(
        @get:NotBlank
        @get:Pattern(regexp = AppRegexp.User.Password)
        val currentPassword: String,

        @get:NotBlank
        @get:Pattern(regexp = AppRegexp.User.Password)
        val newPassword: String
)

data class UserModifyChangeMobile(
        @get:NotBlank
        val pinAuthCode: String
)

data class UserCheckMobile(
        @get:NotBlank
        @get:Pattern(regexp = AppRegexp.User.Password)
        val password: String
)