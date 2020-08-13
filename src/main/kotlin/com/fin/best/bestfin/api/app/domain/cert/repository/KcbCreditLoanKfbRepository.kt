package com.fin.best.bestfin.api.app.domain.cert.repository

import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCreditLoanKfb
import org.springframework.data.jpa.repository.JpaRepository

interface KcbCreditLoanKfbRepository : JpaRepository<KcbCreditLoanKfb, Long> {
}