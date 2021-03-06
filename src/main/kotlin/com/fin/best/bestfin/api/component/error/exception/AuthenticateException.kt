package com.fin.best.bestfin.api.component.error.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(value = ["cause", "stackTrace", "suppressed"])
class AuthenticateException(message: String): Exception(message)