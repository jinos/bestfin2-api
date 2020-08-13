package com.fin.best.bestfin.api.app.domain.product.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class ProductItem {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "idx")
    var idx: Long? = null
    var seq: Long = 0
    var title: String = ""
    var rate: Float = 0.0F
    @get:Enumerated(EnumType.STRING)
    var useYn: AppConst.YN = AppConst.YN.Y
    var createUserNo: Long = 0
    var issueAt: LocalDateTime = LocalDateTime.now()

    @get:ManyToOne
    @get:JoinColumn(name = "seq", referencedColumnName = "idx", insertable = false, updatable = false)
    var productOption: ProductOption? = null

    constructor()
    constructor(seq: Long, title: String, rate: Float, createUserNo: Long, issueAt: LocalDateTime) {
        this.seq = seq
        this.title = title
        this.rate = rate
        this.createUserNo = createUserNo
        this.issueAt = issueAt
    }

}