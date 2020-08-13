package com.fin.best.bestfin.api.app.domain.cert.repository

import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCreditData
import org.springframework.data.jpa.repository.JpaRepository

interface KcbCreditDataRepository : JpaRepository<KcbCreditData, Long> {
}