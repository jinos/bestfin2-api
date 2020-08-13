package com.fin.best.bestfin.api.app.domain.product.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class ProductOption {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "idx")
    var idx: Long? = null
    var seq: Long = 0
    var dclsMonth: String = ""
    var finCoNo: String = ""
    var finPrdtCd: String = ""
    @get:Enumerated(EnumType.STRING)
    var mrtgType: AppConst.Product.MrtgType = AppConst.Product.MrtgType.N
    var mrtgTypeNm: String = ""
    @get:Enumerated(EnumType.STRING)
    var rpayType: AppConst.Product.RpayType = AppConst.Product.RpayType.N
    var rpayTypeNm: String = ""
    @get:Enumerated(EnumType.STRING)
    var lendRateType: AppConst.Product.LendRateType = AppConst.Product.LendRateType.N
    var lendRateTypeNm: String = ""

    var lendRateMin: String = ""
    @get:Enumerated(EnumType.STRING)
    var lendRateMinStatus: AppConst.YN = AppConst.YN.N

    var lendRateMax: String = ""
    @get:Enumerated(EnumType.STRING)
    var lendRateMaxStatus: AppConst.YN = AppConst.YN.N

    var lendRateAvg: String = ""
    @get:Enumerated(EnumType.STRING)
    var lendRateAvgStatus: AppConst.YN = AppConst.YN.N

    var crdtPrdtType: String = ""
    @get:Enumerated(EnumType.STRING)
    var crdtLendRateType: AppConst.Product.CrdtLendRateType = AppConst.Product.CrdtLendRateType.N

    var crdtLendRateTypeNm: String = ""

    var crdtGrad1: String = ""
    @get:Enumerated(EnumType.STRING)
    var crdtGrad1Status: AppConst.YN = AppConst.YN.N

    var crdtGrad4: String = ""
    @get:Enumerated(EnumType.STRING)
    var crdtGrad4Status: AppConst.YN = AppConst.YN.N

    var crdtGrad5: String = ""
    @get:Enumerated(EnumType.STRING)
    var crdtGrad5Status: AppConst.YN = AppConst.YN.N

    var crdtGrad6: String = ""
    @get:Enumerated(EnumType.STRING)
    var crdtGrad6Status: AppConst.YN = AppConst.YN.N

    var crdtGrad10: String = ""
    @get:Enumerated(EnumType.STRING)
    var crdtGrad10Status: AppConst.YN = AppConst.YN.N

    var crdtGradAvg: String = ""
    @get:Enumerated(EnumType.STRING)
    var crdtGradAvgStatus: AppConst.YN = AppConst.YN.N

    @get:Enumerated(EnumType.STRING)
    var loanType: AppConst.Product.LoanType = AppConst.Product.LoanType.M

    var weight: Int = 0

    var issueAt: LocalDateTime? = null
    @get:Enumerated(EnumType.STRING)
    var useYn: AppConst.YN = AppConst.YN.Y
    @get:Enumerated(EnumType.STRING)
    var issueYn: AppConst.YN = AppConst.YN.Y
    var updateAt: LocalDateTime? = null
    var createUserNo: Long? = null
    var updateUserNo: Long? = null

    @get:ManyToOne
    @get:JoinColumn(name = "seq", referencedColumnName = "seq", insertable = false, updatable = false)
    var productBase: ProductBase? = null

    constructor()
    constructor(seq: Long, dclsMonth: String, finCoNo: String, finPrdtCd: String,
                mrtgType: AppConst.Product.MrtgType, mrtgTypeNm: String, rpayType: AppConst.Product.RpayType,
                rpayTypeNm: String, lendRateType: AppConst.Product.LendRateType, lendRateTypeNm: String,
                lendRateMin: String, lendRateMax: String, lendRateAvg: String, weight: Int,
                lendRateMinStatus: AppConst.YN, lendRateMaxStatus: AppConst.YN, lendRateAvgStatus: AppConst.YN,
                crdtPrdtType: String, crdtLendRateType: AppConst.Product.CrdtLendRateType, crdtLendRateTypeNm: String,
                crdtGrad1: String, crdtGrad4: String, crdtGrad5: String, crdtGrad6: String, crdtGrad10: String, crdtGradAvg: String,
                crdtGrad1Status: AppConst.YN, crdtGrad4Status: AppConst.YN, crdtGrad5Status: AppConst.YN, crdtGrad6Status: AppConst.YN,
                crdtGrad10Status: AppConst.YN, crdtGradAvgStatus: AppConst.YN,
                loanType: AppConst.Product.LoanType, issueAt: LocalDateTime?, createUserNo: Long) {
        this.seq = seq
        this.dclsMonth = dclsMonth
        this.finCoNo = finCoNo
        this.finPrdtCd = finPrdtCd
        this.mrtgType = mrtgType
        this.mrtgTypeNm = mrtgTypeNm
        this.rpayType = rpayType
        this.rpayTypeNm = rpayTypeNm
        this.lendRateType = lendRateType
        this.lendRateTypeNm = lendRateTypeNm
        this.lendRateMin = lendRateMin
        this.lendRateMax = lendRateMax
        this.lendRateAvg = lendRateAvg
        this.lendRateMinStatus = lendRateMinStatus
        this.lendRateMaxStatus = lendRateMaxStatus
        this.lendRateAvgStatus = lendRateAvgStatus

            this.crdtPrdtType = crdtPrdtType
            this.crdtLendRateType = crdtLendRateType

        this.crdtLendRateTypeNm = crdtLendRateTypeNm
        this.crdtGrad1 = crdtGrad1
        this.crdtGrad1Status = crdtGrad1Status
        this.crdtGrad4 = crdtGrad4
        this.crdtGrad4Status = crdtGrad4Status
        this.crdtGrad5 = crdtGrad5
        this.crdtGrad5Status = crdtGrad5Status
        this.crdtGrad6 = crdtGrad6
        this.crdtGrad6Status = crdtGrad6Status
        this.crdtGrad10 = crdtGrad10
        this.crdtGrad10Status = crdtGrad10Status
        this.crdtGradAvg = crdtGradAvg
        this.crdtGradAvgStatus = crdtGradAvgStatus
        this.weight = weight

        this.loanType = loanType
        this.issueAt = issueAt
        this.createUserNo = createUserNo
    }

}