package com.fin.best.bestfin.api.app.domain.cert.repository

import com.fin.best.bestfin.api.app.domain.cert.entity.KcbCertHistory
import org.springframework.data.jpa.repository.JpaRepository

interface KcbCertHistoryRepository : JpaRepository<KcbCertHistory, Long> {
    fun deleteByCiAndDi(ci: String, di: String)
}