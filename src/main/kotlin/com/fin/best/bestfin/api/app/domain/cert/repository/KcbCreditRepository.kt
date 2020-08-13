package com.fin.best.bestfin.api.app.domain.cert.repository

import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCredit
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface KcbCreditRepository : JpaRepository<KcbCredit, Long> {
    fun findByKcbUserNo(kcbUserNo: String): Optional<KcbCredit>
}