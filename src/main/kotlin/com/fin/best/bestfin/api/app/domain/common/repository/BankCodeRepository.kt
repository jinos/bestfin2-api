package com.fin.best.bestfin.api.app.domain.common.repository

import com.fin.best.bestfin.api.app.domain.common.entity.BankCode
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BankCodeRepository : JpaRepository<BankCode, Long> {
    fun findByOrderByKorCoNm(pageable: Pageable): List<BankCode>
}