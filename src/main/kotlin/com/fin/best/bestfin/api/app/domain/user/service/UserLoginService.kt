package com.fin.best.bestfin.api.app.domain.user.service

import com.fin.best.bestfin.api.app.domain.auth.dao.AuthDao
import com.fin.best.bestfin.api.app.domain.auth.model.AuthLogin
import com.fin.best.bestfin.api.app.domain.auth.model.ClientInfo

import com.fin.best.bestfin.api.app.domain.user.dao.UserDao
import com.fin.best.bestfin.api.component.model.ConditionalResult
import com.fin.best.bestfin.api.component.constants.AppConst
import com.fin.best.bestfin.api.config.security.BestFinAccountService
import com.fin.best.bestfin.api.app.gateway.v1.user.param.UserSignInParams
import com.fin.best.bestfin.api.component.error.exception.AuthLoginUserFailException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.HashMap

@Service
class UserLoginService
@Autowired constructor(
        private val userDao: UserDao,
        private val authDao: AuthDao,
        private val passwordEncoder: PasswordEncoder,
        private val authTokenService: BestFinAccountService
) {
    @Throws(AuthLoginUserFailException::class)
    fun loginUser(params: UserSignInParams): ConditionalResult {
        val login = userDao.fetchUserByUserId(params.userId).orElseThrow { throw AuthLoginUserFailException() }

        //탈퇴 회원
        if (login.status == AppConst.User.UserStatus.SEC || !passwordEncoder.matches(params.password, login.password))
            throw AuthLoginUserFailException()


        login.latestAt = LocalDateTime.now()
        val user = userDao.storeUser(login)
        val authLogin = createAccessToken(
                AppConst.User.LoginType.USER_ID,
                user.userNo!!.toString(),
                params.password
        )

        return return ConditionalResult.Builder()
                .success(true)
                //.result(authLogin)
                .result("")
                .build()
    }

    private fun createAccessToken(
            loginType: AppConst.User.LoginType,
            userName: String,
            password: String
    ): AuthLogin? {
        val clientInfo = ClientInfo(loginType).invoke()
        val clientId = clientInfo.clientId
        val clientSecret = clientInfo.clientSecret

        val parameters: MutableMap<String, String> = HashMap()
        parameters["client_id"] = clientId
        parameters["client_secret"] = clientSecret
        parameters["grant_type"] = "password"
        parameters["username"] = userName
        parameters["password"] = password


        /*val token = authTokenService.getOAuth2AccessToken(
                clientId, clientSecret, parameters
        )*/

        // update login history
        /*return authTokenService.storeLoginHistory(
                loginType = loginType,
                clientId = clientId,
                userNo = userName.toLong(),
                token = token!!
        )*/

        return null
    }








}
