package com.fin.best.bestfin.api.app.domain.cert.repository

import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCreditRatingInfo
import org.springframework.data.jpa.repository.JpaRepository

interface KcbCreditRatingInfoRepository : JpaRepository<KcbCreditRatingInfo, Long> {
}