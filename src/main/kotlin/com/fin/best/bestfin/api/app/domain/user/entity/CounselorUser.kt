package com.fin.best.bestfin.api.app.domain.user.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class CounselorUser {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "counselor_no")
    var counselorNo: Long? = null
    var counselorNm: String = ""
    var counselorId: String = ""
    var pass: String = ""
    var mobileNo: String = ""
    var profile: Long? = null
    var nameCardImg1: Long? = null
    var nameCardImg2: Long? = null
    @get:Enumerated(EnumType.STRING)
    var status: AppConst.User.UserStatus = AppConst.User.UserStatus.NOR
    var issueAt: LocalDateTime = LocalDateTime.now()
    var updateAt: LocalDateTime = LocalDateTime.now()
}