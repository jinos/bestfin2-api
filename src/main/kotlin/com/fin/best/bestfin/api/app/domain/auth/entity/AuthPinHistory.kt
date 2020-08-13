package com.fin.best.bestfin.api.app.domain.auth.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class AuthPinHistory {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "pin_no")
    var pinNo: Int? = null
    @get:Enumerated(EnumType.STRING)
    var pinType: AppConst.User.PinType? = null
    var pinCode: String = ""
    @get:Enumerated(EnumType.STRING)
    var pinStatus: AppConst.User.PinStatus? = null
    @get:Enumerated(EnumType.STRING)
    var userType: AppConst.User.UserType? = null
    var mobileNumber: String = ""
    var expireAt: LocalDateTime = LocalDateTime.now()
    var issueAt: LocalDateTime = LocalDateTime.now()


}