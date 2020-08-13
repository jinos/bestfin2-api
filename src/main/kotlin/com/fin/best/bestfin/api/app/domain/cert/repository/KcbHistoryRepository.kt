package com.fin.best.bestfin.api.app.domain.cert.repository

import com.fin.best.bestfin.api.app.domain.cert.entity.KcbHistory
import com.fin.best.bestfin.api.app.domain.cert.entity.KcbHistoryId
import org.springframework.data.jpa.repository.JpaRepository

interface KcbHistoryRepository : JpaRepository<KcbHistory, KcbHistoryId> {
}