package com.fin.best.bestfin.api.app.domain.auth.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class AuthLoginHistory {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "login_no")
    var loginNo: Int? = null
    @get:Enumerated(EnumType.STRING)
    var loginType: AppConst.User.LoginType? = null
    var loginUser: Long = 0
    var loginAt: LocalDateTime = LocalDateTime.now()
    var sessionId: String = ""
    @get:Enumerated(EnumType.STRING)
    var useYn: AppConst.YN = AppConst.YN.Y
    var issueAt: LocalDateTime = LocalDateTime.now()

    constructor()
    constructor(loginType: AppConst.User.LoginType, loginUser: Long, loginAt: LocalDateTime, issueAt: LocalDateTime) {
        this.loginType = loginType
        this.loginUser = loginUser
        this.loginAt = loginAt
        this.issueAt = issueAt
    }
}