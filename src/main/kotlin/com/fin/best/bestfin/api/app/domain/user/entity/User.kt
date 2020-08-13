package com.fin.best.bestfin.api.app.domain.user.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class User {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "user_no")
    var userNo: Long? = null
    var userId: String = ""
    var password: String = ""
    @get:Enumerated(EnumType.STRING)
    var status: AppConst.User.UserStatus = AppConst.User.UserStatus.NOR
    var email: String = ""
    @get:Enumerated(EnumType.STRING)
    var serviceAgreement: AppConst.YN = AppConst.YN.Y
    @get:Enumerated(EnumType.STRING)
    var piAgreement: AppConst.YN = AppConst.YN.Y
    @get:Enumerated(EnumType.STRING)
    var certYn: AppConst.YN = AppConst.YN.Y
    var certNo: Int = 0
    var creditNo: Int = 0
    var ci: String = ""
    var di: String = ""
    var name: String = ""
    var birth: String = ""
    @get:Enumerated(EnumType.STRING)
    var gender: AppConst.Gender = AppConst.Gender.UNKNOWN
    var mobileNumber: String = ""
    var annualIncome: Int = 0
    var createAt: LocalDateTime = LocalDateTime.now()
    var issueAt: LocalDateTime = LocalDateTime.now()
    var latestAt: LocalDateTime = LocalDateTime.now()
    var pinNo: Int? = null

    constructor()
    constructor(userId: String, password: String, status: AppConst.User.UserStatus, email: String, serviceAgreement: AppConst.YN, piAgreement: AppConst.YN, certYn: AppConst.YN, certNo: Int, creditNo: Int, ci: String, di: String, name: String, birth: String, gender: AppConst.Gender, mobileNumber: String, annualIncome: Int) {
        this.userId = userId
        this.password = password
        this.status = status
        this.email = email
        this.serviceAgreement = serviceAgreement
        this.piAgreement = piAgreement
        this.certYn = certYn
        this.certNo = certNo
        this.creditNo = creditNo
        this.ci = ci
        this.di = di
        this.name = name
        this.birth = birth
        this.gender = gender
        this.mobileNumber = mobileNumber
        this.annualIncome = annualIncome
    }
}