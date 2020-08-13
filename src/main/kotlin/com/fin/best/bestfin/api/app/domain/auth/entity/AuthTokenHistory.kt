package com.fin.best.bestfin.api.app.domain.auth.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class AuthTokenHistory {

    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "token_no")
    var tokenNo: Long? = null
    var loginNo: Long = 0
    var clientId: String = ""

    @get:Enumerated(EnumType.STRING)
    var tokenIssueType: AppConst.User.AuthToken.TokenIssueType = AppConst.User.AuthToken.TokenIssueType.ACCESS
    var accessToken: String = ""
    var refreshToken: String = ""
    var authority: String = ""
    var issueAt: LocalDateTime = LocalDateTime.now()

    constructor()
    constructor(loginNo: Long, clientId: String, tokenIssueType: AppConst.User.AuthToken.TokenIssueType, accessToken: String, refreshToken: String, authority: String, issueAt: LocalDateTime) {
        this.loginNo = loginNo
        this.clientId = clientId
        this.tokenIssueType = tokenIssueType
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        this.authority = authority
        this.issueAt = issueAt
    }

}