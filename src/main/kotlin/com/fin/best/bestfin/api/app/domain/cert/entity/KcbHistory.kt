package com.fin.best.bestfin.api.app.domain.cert.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@IdClass(KcbHistoryId::class)
class KcbHistory {
    @get:Id
    @get:Column(name = "kcb_date")
    var kcbDate: Long? = null
    @get:Id
    @get:Column(name = "kcb_no")
    var kcbNo: Long? = null
    @get:Enumerated(EnumType.STRING)
    var reqType: AppConst.Kcb.ReqType = AppConst.Kcb.ReqType.SCD_2_1
    @get:Enumerated(EnumType.STRING)
    var reqStatus: AppConst.Kcb.ReqStatus = AppConst.Kcb.ReqStatus.ON
    var certNo: Long = 0
    var creditNo: Long = 0

    @get:Lob
    var sendData: ByteArray = byteArrayOf()
    @get:Lob
    var recvData: ByteArray = byteArrayOf()
    var createAt: LocalDateTime = LocalDateTime.now()
    var issueAt: LocalDateTime = LocalDateTime.now()
}

data class KcbHistoryId(
        var kcbDate: Long = 0,
        var kcbNo: Long = 0
) : Serializable