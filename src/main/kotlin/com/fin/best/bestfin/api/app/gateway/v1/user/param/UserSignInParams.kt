package com.fin.best.bestfin.api.app.gateway.v1.user.param

import javax.validation.constraints.NotBlank

data class UserSignInParams(
        @get:NotBlank
        val userId: String,

        @get:NotBlank
        val password: String
)