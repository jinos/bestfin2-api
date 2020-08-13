package com.fin.best.bestfin.api.app.domain.user.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class AgentUser {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "agent_no")
    var agentNo: Long? = null
    var agentId: String = ""
    var pass: String = ""
    var email: String = ""
    var coNm: String = ""
    var ceoNm: String = ""
    var coNumber: String = ""
    var coFileNo: Long? = null
    var profileFileNo: Long? = null
    var phone: String = ""
    var mobile: String = ""
    var addr: String = ""
    var detailAddr: String = ""
    var lat: Double = 0.0
    var lng: Double = 0.0
    var counselorNo: Long = 0
    @get:Enumerated(EnumType.STRING)
    var useYn: AppConst.YN = AppConst.YN.Y
    var issueAt: LocalDateTime = LocalDateTime.now()
    @get:Enumerated(EnumType.STRING)
    var gender: AppConst.Gender = AppConst.Gender.UNKNOWN
    var updateAdminNo: Long = 0
    var updateAt: LocalDateTime = LocalDateTime.now()
    @get:Enumerated(EnumType.STRING)
    var status: AppConst.User.AgentUser.UserStatus = AppConst.User.AgentUser.UserStatus.WAIT
    var updateAdminAt: LocalDateTime = LocalDateTime.now()
    var reason: String = ""
    var pinNo: Int = 0
    var zipNo: String = ""
    @get:Enumerated(EnumType.STRING)
    var withdrawStatus: AppConst.User.AgentUser.WithdrawStatus = AppConst.User.AgentUser.WithdrawStatus.ON
    var withdrawAt: LocalDateTime = LocalDateTime.now()
}