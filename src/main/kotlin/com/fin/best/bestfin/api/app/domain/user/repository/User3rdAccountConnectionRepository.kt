package com.fin.best.bestfin.api.app.domain.user.repository

import com.fin.best.bestfin.api.app.domain.user.entity.User3rdAccountConnection
import com.fin.best.bestfin.api.app.domain.user.entity.User3rdAccountConnectionId
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface User3rdAccountConnectionRepository : JpaRepository<User3rdAccountConnection, User3rdAccountConnectionId> {
    fun findByUserNo(userNo: Long): List<User3rdAccountConnection>
    fun findByAccountNo(accountNo: Long): Optional<User3rdAccountConnection>
}