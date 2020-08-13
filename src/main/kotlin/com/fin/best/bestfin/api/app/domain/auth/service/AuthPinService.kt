package com.fin.best.bestfin.api.app.domain.auth.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fin.best.bestfin.api.app.domain.auth.dao.AuthDao
import com.fin.best.bestfin.api.app.domain.auth.entity.AuthPinHistory
import com.fin.best.bestfin.api.component.constants.AppConst
import com.fin.best.bestfin.api.component.error.exception.PinCertException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthPinService
@Autowired constructor(
        private val authDao: AuthDao,
        private val objectMapper: ObjectMapper
) {
    @Throws(PinCertException::class)
    fun verifyPinAuthCode(pinNo: Int): AuthPinHistory {
        val authPinHistory = authDao.fetchAuthPinCode(pinNo.toLong()).orElseThrow { throw PinCertException() }

        if (authPinHistory.pinStatus != AppConst.User.PinStatus.OK)
            throw PinCertException()

        authPinHistory.pinStatus = AppConst.User.PinStatus.DONE
        authPinHistory.issueAt = LocalDateTime.now()
        return authDao.storeAuthPinHistory(authPinHistory)
    }
}
