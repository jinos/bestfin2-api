package com.fin.best.bestfin.api.component.error.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class FileHandleException(message: String): Exception(message)

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class FailUploadFileHandleException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(910, "파일 업로드에 실패하였습니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class WrongExcelTemplateHandleException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(911, "잘못된 엑셀 템플릿 형식입니다.")
}

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class NotExistFileHandleException(errorCode: Int, message: String) : ApiException(errorCode, message) {
    constructor() : this(912, "파일이 존재하지 않습니다.")
}