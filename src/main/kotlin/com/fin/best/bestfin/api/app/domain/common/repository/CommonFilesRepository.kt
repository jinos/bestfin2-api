package com.fin.best.bestfin.api.app.domain.common.repository

import com.fin.best.bestfin.api.app.domain.common.entity.CommonFiles
import org.springframework.data.jpa.repository.JpaRepository

interface CommonFilesRepository : JpaRepository<CommonFiles, Long> {
}