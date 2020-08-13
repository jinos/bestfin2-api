package com.fin.best.bestfin.api.config.security

import com.fin.best.bestfin.api.component.constants.AppConst.Request.RequestHeaderAccessTokenKey
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


class AuthenticationFilter(private val bestFinAccountService: BestFinAccountService) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, filterChain: FilterChain?) {
        val httpRequest = request as HttpServletRequest
        val accessToken = httpRequest.getHeader(RequestHeaderAccessTokenKey)

        try {
            if (accessToken == null) {
                SecurityContextHolder.getContext().authentication = null
            } else {
                SecurityContextHolder.getContext().authentication = bestFinAccountService.getAuthentication(accessToken)
            }
        } catch (ex: Exception) {
            SecurityContextHolder.getContext().authentication = null
        }
        filterChain!!.doFilter(request, response)
    }
}