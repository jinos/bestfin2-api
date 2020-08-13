package com.fin.best.bestfin.api.app.domain.cert.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class KcbCreditData {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "credit_data_no")
    var creditDataNo: Int? = null
    var creditNo: Long? = null
    @get:Lob
    var creditData: ByteArray = byteArrayOf()
    var recvCount17: Int = 0
    var recvCount18: Int = 0
    var recvCount19: Int = 0
    var recvCount20: Int = 0
    var recvCount23: Int = 0
    var recvCount24: Int = 0
    var recvCount25: Int = 0
    var recvCount26: Int = 0
    var recvCount27: Int = 0
    var recvCount28: Int = 0
    var recvCount29: Int = 0
    var recvCount31: Int = 0
    var recvCount32: Int = 0
    var recvCount33: Int = 0
    var recvCount35: Int = 0
    var recvCount36: Int = 0
    var recvCount37: Int = 0
    var recvCount38: Int = 0
    var recvCount39: Int = 0
    var recvCount103: Int = 0
    var issueAt: LocalDateTime = LocalDateTime.now()
}