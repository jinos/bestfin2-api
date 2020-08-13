package com.fin.best.bestfin.api.component.error.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
open class ApiException(errorCode: Int, message: String, result: Any? = null) : RuntimeException(message) {
    var errorCode: Int = errorCode
    override var message: String = message
    var result: Any? = result
}
