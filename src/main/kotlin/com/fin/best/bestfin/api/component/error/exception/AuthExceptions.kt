package com.fin.best.bestfin.api.component.error.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fin.best.bestfin.api.component.error.exception.ApiException

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class NotFoundPinException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(2000, "인증 PIN을 찾을 수 없습니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class InvalidPinCodeException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(2001, "인증 PIN이 유효하지 않습니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class ExpiredPinCodeException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(2002, "인증 PIN이 만료되었습니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class InvalidDeviceTokenException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(2003, "Device 토큰이 유효하지 않습니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class InvalidAccessTokenException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(2004, "Access 토큰이 유효하지 않습니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class InvalidRefreshTokenException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(2005, "Refresh 토큰이 유효하지 않습니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class InvalidAuthUserException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(2006, "인증 사용자가 유효하지 않습니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class AuthLoginUserFailException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(10100, "아이디 또는 비밀번호가 올바르지 않습니다.")
}