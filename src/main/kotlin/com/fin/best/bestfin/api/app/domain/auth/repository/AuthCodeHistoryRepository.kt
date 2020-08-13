package com.fin.best.bestfin.api.app.domain.auth.repository

import com.fin.best.bestfin.api.app.domain.auth.entity.AuthCodeHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuthCodeHistoryRepository : JpaRepository<AuthCodeHistory, String> {
    fun findByAuthCode(certAuthCode: String): Optional<AuthCodeHistory>

}