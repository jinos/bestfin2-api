package com.fin.best.bestfin.api.app.domain.auth.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class AuthTokenLog {

    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "auth_no")
    var authNo: Long? = null
    @get:Column(name = "device_no")
    var deviceNo: Long? = null

    var userIdentifier: Long? = null
    @get:Enumerated(EnumType.STRING)
    var userType: AppConst.User.UserType = AppConst.User.UserType.USR
    @get:Enumerated(EnumType.STRING)
    var authType: AppConst.User.AuthToken.TokenType = AppConst.User.AuthToken.TokenType.ACC
    var token: String = ""
    var expiredAt: LocalDateTime = LocalDateTime.now()
    var expiredTime: Long? = null
    @get:Enumerated(EnumType.STRING)
    var expiredTimeUnit: AppConst.User.AuthToken.ExpiredTimeUnit = AppConst.User.AuthToken.ExpiredTimeUnit.DAY
    @get:Enumerated(EnumType.STRING)
    var useYn: AppConst.YN = AppConst.YN.Y

    fun initAccessToken(userIdentifier: Long, userType: AppConst.User.UserType,
                        deviceNo: Long, token: String): AuthTokenLog {
        this.userIdentifier = userIdentifier
        this.userType = userType
        this.deviceNo = deviceNo
        this.token = token
        this.authType = AppConst.User.AuthToken.TokenType.ACC
        this.expiredAt = LocalDateTime.now().plusHours(AppConst.Cache.AccessTokenExpiredValue)
        this.expiredTime = AppConst.Cache.AccessTokenExpiredValue
        this.expiredTimeUnit = AppConst.User.AuthToken.ExpiredTimeUnit.HOURS
        this.useYn = AppConst.YN.Y

        return this
    }

    fun initRefreshToken(userIdentifier: Long, userType: AppConst.User.UserType,
                         deviceNo: Long, token: String): AuthTokenLog {
        this.userIdentifier = userIdentifier
        this.userType = userType
        this.deviceNo = deviceNo
        this.token = token
        this.authType = AppConst.User.AuthToken.TokenType.REF
        this.expiredAt = LocalDateTime.now().plusDays(AppConst.Cache.RefreshTokenTokenExpiredValue)
        this.expiredTime = AppConst.Cache.RefreshTokenTokenExpiredValue
        this.expiredTimeUnit = AppConst.User.AuthToken.ExpiredTimeUnit.DAY
        this.useYn = AppConst.YN.Y

        return this
    }
}