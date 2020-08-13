package com.fin.best.bestfin.api.app.domain.auth.repository

import com.fin.best.bestfin.api.app.domain.auth.entity.AuthPinHistory
import org.springframework.data.jpa.repository.JpaRepository

interface AuthPinHistoryRepository : JpaRepository<AuthPinHistory, Long> {
}