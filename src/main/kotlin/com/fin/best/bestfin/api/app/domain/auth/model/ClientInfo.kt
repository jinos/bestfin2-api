package com.fin.best.bestfin.api.app.domain.auth.model

import com.fin.best.bestfin.api.component.constants.AppConst

class ClientInfo {
    var loginType: AppConst.User.LoginType = AppConst.User.LoginType.USER_ID
    var clientId: String = ""
    var clientSecret: String = "8D3F44CED8AFF7A0A84E4A29715E9F8C"

    constructor()
    constructor(loginType: AppConst.User.LoginType) {
        this.loginType = loginType
    }

    fun invoke(): ClientInfo {
        when (loginType) {
            AppConst.User.LoginType.USER_ID -> {
                clientId = "best_lc-user"
            }
            AppConst.User.LoginType.USER_3RD -> {
                clientId = "best_lc-user3rd"
            }
            AppConst.User.LoginType.ADMIN -> {
                clientId = "best_lc-admin"
            }
            AppConst.User.LoginType.AGENT -> {
                clientId = "best_lc-agent"
            }
            AppConst.User.LoginType.COUNSELOR -> {
                clientId = "best_lc-counselor"
            }
        }

        return this
    }
}