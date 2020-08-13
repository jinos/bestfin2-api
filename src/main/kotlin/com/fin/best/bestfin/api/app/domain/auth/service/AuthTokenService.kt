package com.fin.best.bestfin.api.app.domain.auth.service

import com.fin.best.bestfin.api.app.domain.auth.dao.AuthDao
import com.fin.best.bestfin.api.app.domain.auth.entity.AuthLoginHistory
import com.fin.best.bestfin.api.app.domain.auth.entity.AuthTokenHistory
import com.fin.best.bestfin.api.app.domain.auth.entity.AuthTokenLog
import com.fin.best.bestfin.api.app.domain.auth.model.AuthDetail
import com.fin.best.bestfin.api.app.domain.auth.model.AuthLogin
import com.fin.best.bestfin.api.app.domain.cache.entity.AuthCache
import com.fin.best.bestfin.api.app.domain.cache.service.AuthCacheService
import com.fin.best.bestfin.api.app.domain.user.dao.UserDao
import com.fin.best.bestfin.api.component.constants.AppConst
import com.fin.best.bestfin.api.component.error.exception.InvalidAccessTokenException
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthTokenService
(
        private val authCacheService: AuthCacheService,
        private val authDao: AuthDao,
        private val userDao: UserDao/*,
        private val clientDetailsService: ClientDetailsService,
        private val tokenEndpoint: TokenEndpoint*/
) {
    @Throws(InvalidAccessTokenException::class)
    fun verifyAccessToken(accessToken: String): AuthCache {
        // not found access token in redis
        val authCache = authCacheService.fetchAuthCache(accessToken) ?: throw InvalidAccessTokenException()

        // expired access token
        if (LocalDateTime.now().isAfter(authCache.expiredAt)) {
            throw InvalidAccessTokenException()
        }

        return authCache
    }

    fun fetchTokens(): List<AuthTokenLog> {
        return authDao.fetchTokens()
    }

    fun getOAuth2AccessToken(
            clientId: String,
            clientSecret: String,
            parameters: Map<String, String>
    ): OAuth2AccessToken? {
        // Get client details
        /*val clientDetails = clientDetailsService.loadClientByClientId(clientId)

        // Create principal and auth token
        val userPrincipal: org.springframework.security.core.userdetails.User =
                org.springframework.security.core.userdetails.User(
                        clientId, clientSecret, true, true, true, true, clientDetails.authorities
                )

        val principal: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                userPrincipal, clientSecret, clientDetails.authorities
        )

        // Set security current context
        SecurityContextHolder.getContext().authentication = principal

        return tokenEndpoint.postAccessToken(principal, parameters).body*/

        return null
    }

    fun storeLoginHistory(
            loginType: AppConst.User.LoginType,
            clientId: String,
            userNo: Long,
            token: OAuth2AccessToken
    ): AuthLogin {
        // destroy active login session
        destroyMyActiveSession(loginType, userNo)

        // save login history
        val loginHistory = authDao.storeLoginHistory(AuthLoginHistory(
                loginUser = userNo,
                loginType = loginType,
                loginAt = LocalDateTime.now(),
                issueAt = LocalDateTime.now()
        ))

        // save token history
        val tokenHistory = storeTokenHistory(
                loginHistory = loginHistory,
                clientId = clientId,
                tokenIssueType = AppConst.User.AuthToken.TokenIssueType.ACCESS,
                token = token
        )

        return AuthLogin(
                login = loginHistory,
                token = tokenHistory,
                detail = getAuthDetail(loginHistory)
        )
    }

    private fun destroyMyActiveSession(loginType: AppConst.User.LoginType, userNo: Long) {
        when (loginType) {
            AppConst.User.LoginType.USER_ID -> {
                //val user3rdAccountConnections =
            }
            AppConst.User.LoginType.USER_3RD -> {

            }
            AppConst.User.LoginType.AGENT -> {

            }
            AppConst.User.LoginType.COUNSELOR -> {

            }
            AppConst.User.LoginType.ADMIN -> {

            }
        }
    }

    private fun storeTokenHistory(loginHistory: AuthLoginHistory, clientId: String, tokenIssueType: AppConst.User.AuthToken.TokenIssueType, token: OAuth2AccessToken): AuthTokenHistory {
        val authorities = token.additionalInformation["authorities"] as List<String>
        return authDao.storeTokenHistory(AuthTokenHistory(
                loginNo = loginHistory.loginNo!!.toLong(),
                clientId = clientId,
                tokenIssueType = tokenIssueType,
                accessToken = token.value,
                refreshToken = token.refreshToken.value,
                authority = authorities.get(0),
                issueAt = LocalDateTime.now()
        ))
    }

    @Throws(IllegalStateException::class)
    private fun getAuthDetail(loginHistory: AuthLoginHistory): AuthDetail? {
        return when (loginHistory.loginType) {
            AppConst.User.LoginType.USER_ID -> {
                val user = userDao.fetchUser(loginHistory.loginUser).orElseThrow { throw IllegalStateException("not found user") }
                AuthDetail(
                        uno = user.userNo!!,
                        id = user.userId,
                        name = user.name,
                        email = user.email
                )
            }
            AppConst.User.LoginType.USER_3RD -> {
                val user3rdAccountConnection = userDao.fetchUser3rdAccountConnectionByAccountNo(loginHistory.loginUser)
                        .orElseThrow { throw IllegalStateException("not found user 3rd account connection") }
                val user = userDao.fetchUser(user3rdAccountConnection.userNo!!)
                        .orElseThrow { throw IllegalStateException("not found user") }
                AuthDetail(
                        uno = user.userNo!!,
                        id = user.userId,
                        name = user.name,
                        email = user.email
                )

            }
            AppConst.User.LoginType.AGENT -> {
                 null
            }
            AppConst.User.LoginType.COUNSELOR -> {
                 null
            }
            AppConst.User.LoginType.ADMIN -> {
                 null
            }
            else -> null
        }
    }
}
