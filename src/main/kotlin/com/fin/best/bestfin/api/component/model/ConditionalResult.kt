package com.fin.best.bestfin.api.component.model

class ConditionalResult private constructor(
        val success: Boolean,
        val code: Int?,
        val message: String?,
        val result: Any?
) {

    class Builder {
        private var code: Int? = null
        private var message: String? = null
        private var success: Boolean = false
        private var result: Any? = null

        fun success(success: Boolean) = this.apply {
            this.success = success
        }

        fun reason(code: Int, message: String) = this.apply {
            this.code = code
            this.message = message
        }

        fun result(result: Any) = this.apply {
            this.result = result
        }

        fun build() = ConditionalResult(
                success = this.success,
                code = this.code ?: 0,
                message = this.message ?: "",
                result = this.result
        )
    }
}