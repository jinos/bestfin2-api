package com.fin.best.bestfin.api.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class BestFinAccessDeniedHandler : AccessDeniedHandler {

    @Qualifier("handlerExceptionResolver")
    @Autowired
    private lateinit var resolver: HandlerExceptionResolver

    override fun handle(request: HttpServletRequest?, response: HttpServletResponse?, accessDeniedException: AccessDeniedException?) {
        resolver.resolveException(request!!, response!!, null, accessDeniedException!!)
    }
}