package com.fin.best.bestfin.api.app.domain.auth.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fin.best.bestfin.api.app.domain.auth.dao.AuthDao
import com.fin.best.bestfin.api.app.domain.auth.entity.AuthPinHistory
import com.fin.best.bestfin.api.app.domain.auth.model.CertAuthCode
import com.fin.best.bestfin.api.app.domain.auth.model.JoinAuthCode
import com.fin.best.bestfin.api.component.error.exception.InvalidCertAuthCodeException
import com.fin.best.bestfin.api.component.error.exception.InvalidJoinAuthCodeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthCodeService
@Autowired constructor(
        private val authDao: AuthDao,
        private val objectMapper: ObjectMapper
) {
    @Throws(InvalidCertAuthCodeException::class)
    fun fetchCertAuthCode(certAuthCode: String): CertAuthCode {
        val authCodeHistory = authDao.fetchCertAuthCode(certAuthCode)

        if (LocalDateTime.now().isAfter(authCodeHistory.expireAt))
            throw InvalidCertAuthCodeException()

        return objectMapper.readValue(authCodeHistory.jsonData, CertAuthCode::class.java)
    }

    @Throws(InvalidCertAuthCodeException::class)
    fun fetchPinAuthCode(pinAuthCode: String): AuthPinHistory {
        val authCodeHistory = authDao.fetchPinAuthCode(pinAuthCode)

        if (LocalDateTime.now().isAfter(authCodeHistory.expireAt))
            throw InvalidCertAuthCodeException()

        return objectMapper.readValue(authCodeHistory.jsonData, AuthPinHistory::class.java)
    }

    @Throws(InvalidJoinAuthCodeException::class)
    fun fetchJoinAuthCode(joinAuthCode: String): JoinAuthCode {
        val authCodeHistory = authDao.fetchAuthCode(joinAuthCode)

        if (LocalDateTime.now().isAfter(authCodeHistory.expireAt))
            throw InvalidJoinAuthCodeException()

        return objectMapper.readValue(authCodeHistory.jsonData, JoinAuthCode::class.java)
    }
}
