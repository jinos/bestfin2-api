package com.fin.best.bestfin.api.component.error.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fin.best.bestfin.api.component.error.exception.ApiException

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class ParameterException(code: Int, message: String): ApiException(code, message)