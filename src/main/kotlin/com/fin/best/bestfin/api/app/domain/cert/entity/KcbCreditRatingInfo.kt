package com.fin.best.bestfin.api.app.domain.cert.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class KcbCreditRatingInfo {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "rating_info_no")
    var ratingInfoNo: Long? = 0
    var creditNo: Long = 0
    var segmentId: String = ""
    var rating: String = ""
    var grade: String = ""
    var issueAt: LocalDateTime = LocalDateTime.now()
}