package com.fin.best.bestfin.api.app.domain.auth.repository

import com.fin.best.bestfin.api.app.domain.auth.entity.AuthTokenLog
import com.fin.best.bestfin.api.component.constants.AppConst
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuthTokenLogRepository : JpaRepository<AuthTokenLog, Long> {
    fun findByTokenAndAuthType(refreshToken: String,
                               tokenType: AppConst.User.AuthToken.TokenType): Optional<AuthTokenLog>

    fun findByUserIdentifierAndUserTypeAndUseYn(userIdentifier: Long,
                                                userType: AppConst.User.UserType,
                                                useYn: AppConst.YN): List<AuthTokenLog>

    fun findByDeviceNoAndUseYn(deviceNo: Long, useYn: AppConst.YN): List<AuthTokenLog>

    fun findByUserTypeAndAuthTypeAndUseYn(userType: AppConst.User.UserType,
                                          authType: AppConst.User.AuthToken.TokenType,
                                          useYn: AppConst.YN): List<AuthTokenLog>

    fun findByUserIdentifierAndUserTypeAndAuthTypeAndUseYn(userIdentifier: Long,
                                                           userType: AppConst.User.UserType,
                                                           authType: AppConst.User.AuthToken.TokenType,
                                                           useYn: AppConst.YN): List<AuthTokenLog>

}