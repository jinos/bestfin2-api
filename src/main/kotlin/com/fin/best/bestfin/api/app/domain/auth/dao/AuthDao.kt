package com.fin.best.bestfin.api.app.domain.auth.dao

import com.fin.best.bestfin.api.app.domain.auth.entity.*
import com.fin.best.bestfin.api.app.domain.auth.repository.*
import com.fin.best.bestfin.api.component.error.exception.InvalidCertAuthCodeException
import com.fin.best.bestfin.api.component.error.exception.InvalidJoinAuthCodeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AuthDao
@Autowired constructor(
        private val authTokenLogRepository: AuthTokenLogRepository,
        private val authCodeHistoryRepository: AuthCodeHistoryRepository,
        private val authPinHistoryRepository: AuthPinHistoryRepository,
        private val authLoginHistoryRepository: AuthLoginHistoryRepository,
        private val authTokenHistoryRepository: AuthTokenHistoryRepository
) {
    fun fetchTokens(): List<AuthTokenLog> {
        return authTokenLogRepository.findAll()
    }

    @Throws(InvalidCertAuthCodeException::class)
    fun fetchCertAuthCode(certAuthCode: String): AuthCodeHistory {
        return authCodeHistoryRepository.findByAuthCode(certAuthCode).orElseThrow { throw InvalidCertAuthCodeException() }
    }

    @Throws(InvalidCertAuthCodeException::class)
    fun fetchPinAuthCode(pinAuthCode: String): AuthCodeHistory {
        return authCodeHistoryRepository.findByAuthCode(pinAuthCode).orElseThrow { throw InvalidCertAuthCodeException() }
    }

    fun fetchAuthPinCode(pinNo: Long): Optional<AuthPinHistory> {
        return authPinHistoryRepository.findById(pinNo)
    }

    fun storeAuthPinHistory(authPinHistory: AuthPinHistory): AuthPinHistory {
        return authPinHistoryRepository.save(authPinHistory)
    }

    @Throws(InvalidJoinAuthCodeException::class)
    fun fetchAuthCode(joinAuthCode: String): AuthCodeHistory {
        return authCodeHistoryRepository.findByAuthCode(joinAuthCode).orElseThrow { throw InvalidJoinAuthCodeException() }
    }

    fun storeLoginHistory(authLoginHistory: AuthLoginHistory): AuthLoginHistory {
        return authLoginHistoryRepository.save(authLoginHistory)
    }

    fun storeTokenHistory(authTokenHistory: AuthTokenHistory): AuthTokenHistory {
        return authTokenHistoryRepository.save(authTokenHistory)
    }
}