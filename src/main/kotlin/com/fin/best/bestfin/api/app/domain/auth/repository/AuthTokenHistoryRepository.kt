package com.fin.best.bestfin.api.app.domain.auth.repository

import com.fin.best.bestfin.api.app.domain.auth.entity.AuthTokenHistory
import org.springframework.data.jpa.repository.JpaRepository

interface AuthTokenHistoryRepository : JpaRepository<AuthTokenHistory, Long> {
}