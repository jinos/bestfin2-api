package com.fin.best.bestfin.api.app.domain.common.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class BankCode {
    @get:Id
    @get:Column(name = "fin_co_no")
    var finCoNo: Long? = null
    var korCoNm: String = ""
    @get:Column(name = "file_no")
    var fileNo: Long? = null
    var aliasKorCoNm: String = ""
    var updateUserNo: Long? = null
    var updateAt: LocalDateTime? = null

    @get:ManyToOne(fetch = FetchType.EAGER)
    @get:JoinColumn(name = "file_no", referencedColumnName = "file_no", insertable = false, updatable = false)
    var files: CommonFiles? = null

    constructor()
    constructor(finCoNo: Long, fileNo: Long?, aliasKorCoNm: String, updateUserNo: Long, updateAt: LocalDateTime) {
        this.finCoNo = finCoNo
        this.fileNo = fileNo
        this.aliasKorCoNm = aliasKorCoNm
        this.updateUserNo = updateUserNo
        this.updateAt = updateAt
    }

}