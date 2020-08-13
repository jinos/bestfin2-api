package com.fin.best.bestfin.api.app.domain.cert.repository

import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCreditPersonalInfo
import org.springframework.data.jpa.repository.JpaRepository

interface KcbCreditPersonalInfoRepository : JpaRepository<KcbCreditPersonalInfo, Long> {
}