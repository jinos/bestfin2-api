package com.fin.best.bestfin.api.app.domain.user.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class AdminUser {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "adm_no")
    var admNo: Long? = null
    var admId: String = ""
    var admNm: String = ""
    @JsonIgnore
    var pass: String = ""
    @get:Enumerated(EnumType.STRING)
    var masterYn: AppConst.YN = AppConst.YN.N
    var mobileNo: String = ""
    var latestAt: LocalDateTime? = null
    var createAt: LocalDateTime = LocalDateTime.now()
    @get:Enumerated(EnumType.STRING)
    var useYn: AppConst.YN = AppConst.YN.Y
    var updateAt: LocalDateTime? = null

    constructor()
}