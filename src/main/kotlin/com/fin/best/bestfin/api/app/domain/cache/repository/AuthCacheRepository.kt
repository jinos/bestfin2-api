package com.fin.best.bestfin.api.app.domain.cache.repository

import com.fin.best.bestfin.api.app.domain.cache.entity.AuthCache
import com.fin.best.bestfin.api.component.constants.AppConst
import org.springframework.data.repository.CrudRepository

interface AuthCacheRepository : CrudRepository<AuthCache, String> {
    fun findByDeviceNo(deviceNo: Long): List<AuthCache>
    fun findByUserIdentifierAndUserType(userIdentifier: Long,
                                        userType: AppConst.User.UserType): List<AuthCache>

}