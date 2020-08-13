package com.fin.best.bestfin.api.app.domain.cert.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class KcbCredit {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "credit_no")
    var creditNo: Int? = null
    var kcbUserNo: String = ""
    var kcbAuthNo: String = ""
    var retryCount: Int = 0
    var createAt: LocalDateTime = LocalDateTime.now()
    var issueAt: LocalDateTime = LocalDateTime.now()
}