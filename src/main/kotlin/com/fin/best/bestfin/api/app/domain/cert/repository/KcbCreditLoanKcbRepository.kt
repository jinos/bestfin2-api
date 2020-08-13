package com.fin.best.bestfin.api.app.domain.cert.repository

import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCreditLoanKcb
import org.springframework.data.jpa.repository.JpaRepository

interface KcbCreditLoanKcbRepository : JpaRepository<KcbCreditLoanKcb, Long> {
}