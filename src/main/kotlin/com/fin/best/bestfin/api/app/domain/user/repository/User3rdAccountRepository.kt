package com.fin.best.bestfin.api.app.domain.user.repository

import com.fin.best.bestfin.api.app.domain.user.entity.User3rdAccount
import org.springframework.data.jpa.repository.JpaRepository

interface User3rdAccountRepository : JpaRepository<User3rdAccount, Long> {
}