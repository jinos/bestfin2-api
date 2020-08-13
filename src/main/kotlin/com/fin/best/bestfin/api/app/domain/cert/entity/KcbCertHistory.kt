package com.fin.best.bestfin.api.app.domain.cert.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class KcbCertHistory {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "cert_no")
    var certNo: Int? = null
    var mobileNumber: String = ""
    var authName: String = ""
    var birth: Int = 0
    var provider: String = ""
    @get:Enumerated(EnumType.STRING)
    var gender: AppConst.Kcb.Gender = AppConst.Kcb.Gender.U
    var ci: String = ""
    var di: String = ""
    @get:Enumerated(EnumType.STRING)
    var authStatus: AppConst.User.AuthStatus = AppConst.User.AuthStatus.ON
    var kcbUserReqCode: String = ""
    var kcbUserNo: String = ""
    var issueAt: LocalDateTime = LocalDateTime.now()
}