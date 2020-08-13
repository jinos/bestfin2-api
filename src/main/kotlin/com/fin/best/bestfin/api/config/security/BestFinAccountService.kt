package com.fin.best.bestfin.api.config.security

import com.fin.best.bestfin.api.app.domain.auth.service.AuthTokenService
import com.fin.best.bestfin.api.component.constants.AppConst
import com.fin.best.bestfin.api.config.security.model.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class BestFinAccountService
@Autowired constructor(
        private val authService: AuthTokenService
) {
    // 토큰으로 인증 정보를 조회
    fun getAuthentication(accessToken: String): Authentication? {
        val authCache = authService.verifyAccessToken(accessToken)

        when (authCache.userType) {
            AppConst.User.UserType.USR -> {
                val userPrincipal = UserPrincipal(
                        username = authCache.userType.name + ":" + authCache.userIdentifier,
                        password = "",
                        authorities = setOf(SimpleGrantedAuthority("ROLE_USER"))
                )
                return UsernamePasswordAuthenticationToken(
                        userPrincipal,
                        null,
                        userPrincipal.authorities
                )
            }
            AppConst.User.UserType.MNG -> {
                val userPrincipal = UserPrincipal(
                        username = authCache.userType.name + ":" + authCache.userIdentifier,
                        password = "",
                        authorities = setOf(SimpleGrantedAuthority("ROLE_MANAGER"))
                )
                return UsernamePasswordAuthenticationToken(
                        userPrincipal,
                        null,
                        userPrincipal.authorities
                )
            }
            AppConst.User.UserType.ADM -> {
                val userPrincipal = UserPrincipal(
                        username = authCache.userType.name + ":" + authCache.userIdentifier,
                        password = "",
                        authorities = setOf(SimpleGrantedAuthority("ROLE_ADMIN"))
                )
                return UsernamePasswordAuthenticationToken(
                        userPrincipal,
                        null,
                        userPrincipal.authorities
                )
            }
            else -> return null
        }
    }
}