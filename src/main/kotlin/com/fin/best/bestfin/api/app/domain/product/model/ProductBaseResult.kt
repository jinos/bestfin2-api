package com.fin.best.bestfin.api.app.domain.product.model

data class ProductBaseResult(
        val seq: Long? = null,
        val finCoNo: String? = null,
        val korCoNm: String? = null,
        val finPrdtCd: String? = null,
        val finPrdtNm: String? = null,
        val loanType: String? = null,
        val dclsMonth: String? = null,
        val useYn: String? = null
) {

}
