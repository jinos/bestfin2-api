package com.fin.best.bestfin.api.component.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fin.best.bestfin.api.component.constants.AppConst
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ResponseHandler<T> : ResponseEntity<ResponseDataValue> {

    constructor(statusCode: Int, responseBody: T) : super(ResponseDataValue(value = responseBody), parseStatus(statusCode))

    constructor(statusCode: Int, resultCode: Int, resultMessage: String, responseBody: T) : super(ResponseDataValue(code = resultCode, message = resultMessage, value = responseBody), parseStatus(statusCode))
}
fun parseStatus(code: Int): HttpStatus = when (code) {
    100 -> HttpStatus.CONTINUE
    200 -> HttpStatus.OK
    201 -> HttpStatus.CREATED
    204 -> HttpStatus.NO_CONTENT
    205 -> HttpStatus.RESET_CONTENT
    300 -> HttpStatus.MULTIPLE_CHOICES
    301 -> HttpStatus.MOVED_PERMANENTLY
    302 -> HttpStatus.FOUND
    306 -> HttpStatus.SWITCHING_PROTOCOLS
    400 -> HttpStatus.BAD_REQUEST
    401 -> HttpStatus.UNAUTHORIZED
    402 -> HttpStatus.PAYMENT_REQUIRED
    403 -> HttpStatus.FORBIDDEN
    404 -> HttpStatus.NOT_FOUND
    405 -> HttpStatus.METHOD_NOT_ALLOWED
    406 -> HttpStatus.NOT_ACCEPTABLE
    408 -> HttpStatus.REQUEST_TIMEOUT
    409 -> HttpStatus.CONFLICT
    412 -> HttpStatus.PRECONDITION_FAILED
    415 -> HttpStatus.UNSUPPORTED_MEDIA_TYPE
    else -> HttpStatus.INTERNAL_SERVER_ERROR
}

class ResponseDataValue {
    val status: String
    val code: Int
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val value: Any?

    constructor() {
        this.code = AppConst.Response.DefaultEmptyCode
        this.status = AppConst.Response.DefaultEmptyMessage
        this.value = null
    }

    constructor(state: Boolean, value: Any?) {
        this.code = 200
        this.status = if (state) AppConst.Response.DefaultSuccessMessage else AppConst.Response.DefaultFailedMessage
        this.value = value
    }

    constructor(value: Any?) {
        this.code = AppConst.Response.DefaultSuccessCode
        this.status = AppConst.Response.DefaultSuccessMessage
        this.value = value
    }

    constructor(code: Int?, message: String?, value: Any?) {
        this.code = code ?: AppConst.Response.DefaultEmptyCode
        this.status = message ?: AppConst.Response.DefaultEmptyMessage
        this.value = value
    }

    override fun toString(): String {
        return "status=$status, code=$code, value=${value.toString()}"
    }
}