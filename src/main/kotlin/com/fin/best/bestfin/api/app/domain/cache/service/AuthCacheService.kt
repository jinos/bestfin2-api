package com.fin.best.bestfin.api.app.domain.cache.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fin.best.bestfin.api.app.domain.cache.entity.AuthCache
import com.fin.best.bestfin.api.app.domain.cache.repository.AuthCacheRepository
import com.fin.best.bestfin.api.component.constants.AppConst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthCacheService
@Autowired constructor(
        private val authCacheRepository: AuthCacheRepository
) {
    val mapper = jacksonObjectMapper()

    fun fetchAuthCache(accessToken: String): AuthCache? {
        return authCacheRepository.findById(accessToken).orElse(null)
    }

    fun fetchAuthCacheListByUser(userIdentifier: Long,
                                 userType: AppConst.User.UserType): List<AuthCache> {
        return authCacheRepository.findByUserIdentifierAndUserType(userIdentifier, userType)
    }

    fun fetchAuthCacheListByDeviceNo(deviceNo: Long): List<AuthCache> {
        return authCacheRepository.findByDeviceNo(deviceNo)
    }

    fun storeAuthCache(authTokenCache: AuthCache): AuthCache {
        return authCacheRepository.save(authTokenCache)
    }

    fun removeAuthCache(authTokenCache: AuthCache) {
        return authCacheRepository.delete(authTokenCache)
    }
}
