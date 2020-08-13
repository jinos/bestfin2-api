package com.fin.best.bestfin.api.config.adapter

import com.fin.best.bestfin.api.component.model.RequestBuffer
import com.fin.best.bestfin.api.component.utils.LogParser
import com.fin.best.bestfin.api.component.utils.StringUtil
import com.fin.best.bestfin.api.component.constants.AppConst
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApplicationInterceptor : HandlerInterceptorAdapter() {

    private val logger = LoggerFactory.getLogger(ApplicationInterceptor::class.java)
    private val moduleName = "INTERCEPTOR"

    @Autowired lateinit var requestBuffer: RequestBuffer

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val tid = StringUtil.generateTimeBaseUUID()
        MDC.put("transactionId", tid)
        requestBuffer.initialized(
                requestId = tid,
                method = request.method,
                requestURI = request.requestURI,
                queryString = request.queryString ?: AppConst.Delimiters.Dash,
                contentType = request.contentType ?: AppConst.Delimiters.Dash,
                contentLength = request.contentLength,
                remoteIp = request.remoteAddr,
                parameters = request.parameterMap,
                userAgent = request.getHeader(AppConst.Log.UserAgent) ?: AppConst.Delimiters.Unknown,
                incomingTime = System.currentTimeMillis(),
                timestamp = 0,
                domain = AppConst.Log.Domain
        )
        logger.info(LogParser.makeLog(4,
                AppConst.Log.ApplicationName, moduleName, requestBuffer.remoteIp!!, AppConst.Log.Request,
                AppConst.Log.UserAgent, requestBuffer.userAgent!!,
                AppConst.Log.ContentType, requestBuffer.contentType!!,
                AppConst.Log.ContentLength, requestBuffer.contentLength,
                AppConst.Log.Method, requestBuffer.method!!,
                AppConst.Log.Uri, requestBuffer.requestURI!!,
                AppConst.Delimiters.Question, requestBuffer.queryString!!
        ))
        return true
    }


}