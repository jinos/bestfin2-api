package com.fin.best.bestfin.api.app.domain.cache.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fin.best.bestfin.api.component.constants.AppConst
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import java.io.Serializable
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@RedisHash(AppConst.Cache.CachedPrefixAuthHashKey)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class AuthCache(
        @get:Id
        val accessToken: String,
        @field:Indexed
        val deviceNo: Long?,
        @field:Indexed
        val userIdentifier: Long,
        @field:Indexed
        val userType: AppConst.User.UserType,
        @field:JsonIgnore
        var extraData: String?,

        @field:JsonIgnore
        val timeOut: Int,
        @field:JsonIgnore
        val timeUnit: TimeUnit
) : Serializable {
    var issuedAt: LocalDateTime = LocalDateTime.now()

    @get:TimeToLive(unit = TimeUnit.MINUTES)
    var timeToLive: Long = when (timeUnit) {
        TimeUnit.HOURS -> (timeOut * 60).toLong()
        TimeUnit.DAYS -> (timeOut * 60 * 24).toLong()
        else -> timeOut.toLong()
    }

    var expiredAt: LocalDateTime = when (timeUnit) {
        TimeUnit.MINUTES -> LocalDateTime.now().plusMinutes(timeOut.toLong())
        TimeUnit.HOURS -> LocalDateTime.now().plusHours(timeOut.toLong())
        else -> LocalDateTime.now().plusDays(timeOut.toLong())
    }
}