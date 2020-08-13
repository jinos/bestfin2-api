package com.fin.best.bestfin.api.app.domain.auth.model

import com.fin.best.bestfin.api.app.domain.auth.entity.AuthLoginHistory
import com.fin.best.bestfin.api.app.domain.auth.entity.AuthTokenHistory

data class AuthLogin(
        var login: AuthLoginHistory,
        var token: AuthTokenHistory,
        var detail: AuthDetail?
)

data class AuthDetail(
        var uno: Long,
        var id: String,
        var name: String,
        var email: String
)