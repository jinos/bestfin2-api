package com.fin.best.bestfin.api.app.domain.auth.repository

import com.fin.best.bestfin.api.app.domain.auth.entity.AuthLoginHistory
import org.springframework.data.jpa.repository.JpaRepository

interface AuthLoginHistoryRepository : JpaRepository<AuthLoginHistory, Long> {
}