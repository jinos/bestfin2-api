package com.fin.best.bestfin.api.component.model

import com.fasterxml.jackson.annotation.JsonInclude

data class ConditionalMessage(
        val code: Int?,
        val message: String?,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val result: Any?
)