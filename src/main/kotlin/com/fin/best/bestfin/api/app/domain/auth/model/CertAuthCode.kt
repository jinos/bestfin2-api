package com.fin.best.bestfin.api.app.domain.auth.model

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime

data class CertAuthCode(
        var certNo: Long,
        var authCodeType: AppConst.User.AuthCodeType,
        var kcbUserReqCode: String,
        var kcbUserNo: String,
        var ci: String,
        var di: String
) {

}