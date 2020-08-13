package com.fin.best.bestfin.api.app.domain.product.entity

import com.fin.best.bestfin.api.app.domain.product.model.ProductBaseResult
import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@SqlResultSetMapping(name = "ProductBaseResultMapping",
        classes = [ConstructorResult(
                targetClass = ProductBaseResult::class,
                columns = [
                    ColumnResult(name = "seq", type = Long::class),
                    ColumnResult(name = "finCoNo", type = String::class),
                    ColumnResult(name = "korCoNm", type = String::class),
                    ColumnResult(name = "finPrdtCd", type = String::class),
                    ColumnResult(name = "finPrdtNm", type = String::class),
                    ColumnResult(name = "loanType", type = String::class),
                    ColumnResult(name = "dclsMonth", type = String::class),
                    ColumnResult(name = "useYn", type = String::class)
                ]
        )]
)

@Entity
class ProductBase {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "seq")
    var seq: Long? = null
    var dclsMonth: String = ""
    var finCoNo: String = ""
    var korCoNm: String = ""
    var finPrdtCd: String = ""
    var finPrdtNm: String = ""

    var joinWay: String = ""
    @get:Enumerated(EnumType.STRING)
    var joinWayStatus: AppConst.YN = AppConst.YN.N

    var loanInciExpn: String = ""
    @get:Enumerated(EnumType.STRING)
    var loanInciExpnStatus: AppConst.YN = AppConst.YN.N

    var erlyRpayFee: String = ""
    @get:Enumerated(EnumType.STRING)
    var erlyRpayFeeStatus: AppConst.YN = AppConst.YN.N

    var dlyRate: String = ""
    @get:Enumerated(EnumType.STRING)
    var dlyRateStatus: AppConst.YN = AppConst.YN.N

    var loanLmt: String = ""
    @get:Enumerated(EnumType.STRING)
    var loanLmtStatus: AppConst.YN = AppConst.YN.N

    var dclsStrtDay: String = ""
    var dclsEndDay: String = ""
    var finCoSubmDay: String = ""
    var crdtPrdtType: String = ""
    var cbName: String = ""
    var crdtPrdtTypeNm: String = ""

    @get:Enumerated(EnumType.STRING)
    var loanType: AppConst.Product.LoanType = AppConst.Product.LoanType.M
    var webUrl: String = ""

    @get:Enumerated(EnumType.STRING)
    var bankType: AppConst.YN = AppConst.YN.N
    var description: String = ""
    var carCondition: String = ""
    var lendRateMin: String = ""
    var lendRateMax: String = ""
    var weight: Int = 0

    @get:Enumerated(EnumType.STRING)
    var mortgageType: AppConst.Product.MortgageType = AppConst.Product.MortgageType.NONE

    @get:Enumerated(EnumType.STRING)
    var guaranteeType: AppConst.Product.GuaranteeType = AppConst.Product.GuaranteeType.NONE

    var issueAt: LocalDateTime = LocalDateTime.now()

    @get:Enumerated(EnumType.STRING)
    var useYn: AppConst.YN = AppConst.YN.Y
    var updateAt: LocalDateTime? = null
    var createUserNo: Long = 0
    var updateUserNo: Long? = null

    constructor()
    constructor(dclsMonth: String, finCoNo: String, korCoNm: String, finPrdtCd: String, finPrdtNm: String,
                joinWay: String, joinWayStatus: AppConst.YN,
                loanInciExpn: String, loanInciExpnStatus: AppConst.YN,
                erlyRpayFee: String, erlyRpayFeeStatus: AppConst.YN,
                dlyRate: String, dlyRateStatus: AppConst.YN,
                loanLmt: String, loanLmtStatus: AppConst.YN,
                dclsStrtDay: String, dclsEndDay: String, finCoSubmDay: String, crdtPrdtType: String, cbName: String, crdtPrdtTypeNm: String, loanType: AppConst.Product.LoanType, webUrl: String, bankType: AppConst.YN, description: String, carCondition: String, lendRateMin: String, lendRateMax: String, weight: Int, mortgageType: AppConst.Product.MortgageType, guaranteeType: AppConst.Product.GuaranteeType, issueAt: LocalDateTime, createUserNo: Long, useYn: AppConst.YN) {
        this.dclsMonth = dclsMonth
        this.finCoNo = finCoNo
        this.korCoNm = korCoNm
        this.finPrdtCd = finPrdtCd
        this.finPrdtNm = finPrdtNm
        this.joinWay = joinWay
        this.joinWayStatus = joinWayStatus
        this.loanInciExpn = loanInciExpn
        this.loanInciExpnStatus = loanInciExpnStatus
        this.erlyRpayFee = erlyRpayFee
        this.erlyRpayFeeStatus = erlyRpayFeeStatus
        this.dlyRate = dlyRate
        this.dlyRateStatus = dlyRateStatus
        this.loanLmt = loanLmt
        this.loanLmtStatus = loanLmtStatus
        this.dclsStrtDay = dclsStrtDay
        this.dclsEndDay = dclsEndDay
        this.finCoSubmDay = finCoSubmDay
        this.crdtPrdtType = crdtPrdtType
        this.cbName = cbName
        this.crdtPrdtTypeNm = crdtPrdtTypeNm
        this.loanType = loanType
        this.webUrl = webUrl
        this.bankType = bankType
        this.description = description
        this.carCondition = carCondition
        this.lendRateMin = lendRateMin
        this.lendRateMax = lendRateMax
        this.weight = weight
        this.mortgageType = mortgageType
        this.guaranteeType = guaranteeType
        this.issueAt = issueAt
        this.createUserNo = createUserNo
        this.useYn = useYn
    }
}