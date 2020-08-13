package com.fin.best.bestfin.api.app.domain.auth.model

import com.fin.best.bestfin.api.component.constants.AppConst

data class JoinAuthCode(
        var provider: AppConst.User.AuthProvider,
        var providerId: String,
        var accountNo: Long
) {

}