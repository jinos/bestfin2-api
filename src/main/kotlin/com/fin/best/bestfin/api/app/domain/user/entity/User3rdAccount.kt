package com.fin.best.bestfin.api.app.domain.user.entity

import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class User3rdAccount {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "account_no")
    var accountNo: Long? = null
    @get:Enumerated(EnumType.STRING)
    var provider: AppConst.User.AuthProvider = AppConst.User.AuthProvider.LOCAL
    var providerId: String = ""
    var nickname: String = ""
    var email: String = ""
    var imageUrl: String = ""
    var createAt: LocalDateTime = LocalDateTime.now()
    var issueAt: LocalDateTime = LocalDateTime.now()
    var latestAt: LocalDateTime = LocalDateTime.now()
}
