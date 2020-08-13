package com.fin.best.bestfin.api.app.domain.auth.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class AuthCodeHistory {
    @get:Id
    var authCode: String = ""
    @get:Enumerated(EnumType.STRING)
    var codeType: AppConst.User.AuthCodeType = AppConst.User.AuthCodeType.CERT_ON
    var expireAt: LocalDateTime = LocalDateTime.now()
    var jsonData: String = ""
    var issueAt: LocalDateTime = LocalDateTime.now()
}