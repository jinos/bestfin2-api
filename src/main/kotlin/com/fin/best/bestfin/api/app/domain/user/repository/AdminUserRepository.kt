package com.fin.best.bestfin.api.app.domain.user.repository

import com.fin.best.bestfin.api.app.domain.user.entity.AdminUser
import com.fin.best.bestfin.api.component.constants.AppConst
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AdminUserRepository : JpaRepository<AdminUser, Long>, JpaSpecificationExecutor<AdminUser> {
    fun countByAdmNoNotAndAdmIdContainingOrAdmNmContainingAndUseYn(admNo: Long, admId: String, admNm: String, useYn: AppConst.YN): Long
    fun findByAdmNoNotAndAdmIdContainingOrAdmNmContainingAndUseYnOrderByAdmNoDesc(admNo: Long, admId: String, admNm: String, useYn: AppConst.YN, pageable: Pageable): List<AdminUser>
}

