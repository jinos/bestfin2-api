package com.fin.best.bestfin.api.app.domain.cert.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class KcbCreditLoanKcb {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "loan_kcb_no")
    var loanKcbNo: Long? = null
    var creditNo: Long = 0
    var segmentId: String = ""
    var agencyName: String = ""
    var loanTypeCode: String = ""
    var loanDate: String = ""
    var expirationDate: String = ""
    var contractAmount: String = ""
    var loanAmount: String = ""
    var creditYn: String = ""
    var collateralYn: String = ""
    var suretyYn: String = ""
    var groupLoanCode: String = ""
    var overdueRepayLoanYn: String = ""
    var creditRecoverySupportYn: String = ""
    var issueAt: LocalDateTime = LocalDateTime.now()
}