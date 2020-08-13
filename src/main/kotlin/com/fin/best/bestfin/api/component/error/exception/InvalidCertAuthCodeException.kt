package com.fin.best.bestfin.api.component.error.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fin.best.bestfin.api.component.error.exception.ApiException

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class InvalidCertAuthCodeException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(10201, "잘못된 본인 인증 코드 입니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class UserCertException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(10000, "본인 인증이 유효하지 않습니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class UserCiDiDuplicateException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(10001, "이미 가입한 사용자입니다.(본인 인증)")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class UserEmailDuplicateException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(10002, "이미 존재한 이메일입니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class UserIdDuplicateException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(10003, "이미 존재한 아이디입니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class UserInvalidInputPasswordException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(10004, "입력된 비밀번호가 일치하지 않습니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class PinCertException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(10305, "휴대전화번호 인증이 유효하지 않습니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class InvalidJoinAuthCodeException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(10200, "잘못된 가입 인증 코드 입니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class UserSignUp3rdExistException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(10005, "이미 다른 사용자에게 등록된 카카오 계정으로 사용할 수 없습니다.")
}