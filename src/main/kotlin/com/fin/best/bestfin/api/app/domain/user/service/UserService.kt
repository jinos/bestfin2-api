package com.fin.best.bestfin.api.app.domain.user.service

import com.fin.best.bestfin.api.app.domain.auth.service.AuthCodeService
import com.fin.best.bestfin.api.app.domain.auth.service.AuthPinService
import com.fin.best.bestfin.api.app.domain.cert.dao.KcbCertDao
import com.fin.best.bestfin.api.app.domain.user.dao.UserDao
import com.fin.best.bestfin.api.app.domain.user.entity.AdminUser
import com.fin.best.bestfin.api.app.domain.user.entity.User
import com.fin.best.bestfin.api.app.gateway.v1.user.param.UserCheckMobile
import com.fin.best.bestfin.api.app.gateway.v1.user.param.UserModifyChangeEmail
import com.fin.best.bestfin.api.app.gateway.v1.user.param.UserModifyChangeMobile
import com.fin.best.bestfin.api.app.gateway.v1.user.param.UserModifyChangePassword
import com.fin.best.bestfin.api.component.model.ConditionalResult
import com.fin.best.bestfin.api.component.error.exception.ParameterException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService
@Autowired constructor(
        private val userDao: UserDao,
        private val passwordEncoder: PasswordEncoder,
        private val authCodeService: AuthCodeService,
        private val authPinService: AuthPinService,
        private val kcbCertDao: KcbCertDao
) {
    @Throws(IllegalStateException::class)
    fun fetchUser(userNo: Long): User {
        return userDao.fetchUser(userNo).orElseThrow { IllegalStateException("not found user !!!") }
    }

    fun changeUserEamil(userNo: Long, params: UserModifyChangeEmail): User {
        var user = fetchUser(userNo)
        user.email = params.email
        user.issueAt = LocalDateTime.now()
        return userDao.storeUser(user)
    }

    @Throws(ParameterException::class)
    fun changeUserPassword(userNo: Long, params: UserModifyChangePassword): User {
        var user = fetchUser(userNo)
        if (!passwordEncoder.matches(params.currentPassword, user.password))
            throw ParameterException(412, "invalid current password")

        user.password = passwordEncoder.encode(params.newPassword)
        user.issueAt = LocalDateTime.now()
        return userDao.storeUser(user)
    }

    fun changeUserMobile(userNo: Long, params: UserModifyChangeMobile): User {
        var user = fetchUser(userNo)
        // 휴대폰번호 인증 정보 확인
        val pinAuthCodeData = authCodeService.fetchPinAuthCode((params.pinAuthCode))
        val pinNo = pinAuthCodeData.pinNo
        val pinCert = authPinService.verifyPinAuthCode(pinNo!!)

        user.pinNo = pinCert.pinNo!!
        user.mobileNumber = pinCert.mobileNumber
        user.issueAt = LocalDateTime.now()
        return userDao.storeUser(user)
    }

    fun checkUserPassword(userNo: Long, params: UserCheckMobile): User {
        var user = fetchUser(userNo)
        if (!passwordEncoder.matches(params.password, user.password))
            throw ParameterException(412, "invalid current password")

        return user
    }

    fun withdrawUser(userNo: Long): ConditionalResult {
        var user = fetchUser(userNo)

        val user3rdAccountConnections = userDao.fetchUser3rdAccountConnections(userNo)
        user3rdAccountConnections.forEach {
            it.accountNo?.let {
                val account = userDao.fetchUser3rdAccount(it)
                if (account.isPresent)
                    userDao.deleteUser3rdAccount(account.get())
            }

            userDao.deleteUser3rdAccountConnection(it)
        }

        kcbCertDao.deleteKcbCertHistoryCiDi(user.ci, user.di)
        userDao.deleteUser(user)

        return return ConditionalResult.Builder()
                .success(true)
                .build()
    }

    fun countAdminManagers(searchValue: String): Long {
        return userDao.countAdminManagers(searchValue)
    }

    fun fetchAdminManagers(searchValue: String, pageNo: Int, itemsPerPage: Int): List<AdminUser> {
        return userDao.fetchAdminManagers(searchValue = searchValue, pageNo = pageNo, itemsPerPage = itemsPerPage).content as List<AdminUser>
    }

}
