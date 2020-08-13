package com.fin.best.bestfin.api.app.domain.cert.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class KcbCreditPersonalInfo {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "personal_info_no")
    var personalInfoNo: Int? = null
    var creditNo: Int = 0
    var segmentId: String = ""
    var infoCode: String = ""
    var regDate: String = ""
    var postAddress: String = ""
    var address: String = ""
    var phone: String = ""
    var annualIncome: String = ""
    var mobileNumber: String = ""
    var issueAt: LocalDateTime = LocalDateTime.now()
}