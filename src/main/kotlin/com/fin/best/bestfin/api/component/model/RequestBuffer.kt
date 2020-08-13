package com.fin.best.bestfin.api.component.model

import org.springframework.stereotype.Component

@Component
class RequestBuffer {
    var domain: String? = null
    var method: String? = null
    var requestURI: String? = null
    var queryString: String? = null
    var contentType: String? = null
    var userAgent: String? = null
    var requestId: String? = null
    var remoteIp: String? = null
    var contentLength: Int = 0
    var timestamp: Long = 0
    var incomingTime: Long = 0
    //    var outgoingTime: Long = 0
    var parameters: Map<String, Array<String>>? = null

    fun initialized(domain: String, method: String, requestURI: String, queryString: String, contentType: String, userAgent: String, requestId: String, remoteIp: String, contentLength: Int, timestamp: Long, incomingTime: Long, parameters: Map<String, Array<String>>) {
        this.domain = domain
        this.method = method
        this.requestURI = requestURI
        this.queryString = queryString
        this.contentType = contentType
        this.userAgent = userAgent
        this.requestId = requestId
        this.remoteIp = remoteIp
        this.contentLength = contentLength
        this.timestamp = timestamp
        this.incomingTime = incomingTime
        this.parameters = parameters
    }
}