package com.fin.best.bestfin.api.app.domain.cert.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class KcbCreditLoanKfb {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "loan_kfb_no")
    var loanKfbNo: Long? = null
    var creditNo: Long = 0
    var segmentId: String = ""
    var agencyName: String = ""
    var regReasonCode: String = ""
    var loanDate: String = ""
    var changeDate: String = ""
    var amount: String = ""
    var kfbLoanCode: String = ""
    var issueAt: LocalDateTime = LocalDateTime.now()
}