package com.fin.best.bestfin.api.app.domain.user.entity

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(User3rdAccountConnectionId::class)
class User3rdAccountConnection {
    @get:Id
    @get:Column(name = "user_no")
    var userNo: Long? = null
    @get:Id
    @get:Column(name = "account_no")
    var accountNo: Long? = null
    var issueAt: LocalDateTime = LocalDateTime.now()

    constructor()
    constructor(userNo: Long?, accountNo: Long?) {
        this.userNo = userNo
        this.accountNo = accountNo
    }

}

data class User3rdAccountConnectionId(
        var userNo: Long = 0,
        var accountNo: Long = 0
) : Serializable