package com.fin.best.bestfin.api.app.domain.user.repository

import com.fin.best.bestfin.api.app.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun existsByUserId(userId: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByCiAndDi(ci: String, di: String): Optional<User>
    fun findByUserId(userId: String): Optional<User>
}