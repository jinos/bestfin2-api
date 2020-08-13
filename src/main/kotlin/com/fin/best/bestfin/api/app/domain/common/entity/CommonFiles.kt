package com.fin.best.bestfin.api.app.domain.common.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fin.best.bestfin.api.component.constants.AppConst
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class CommonFiles {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "file_no")
    var fileNo: Long? = null
    var fileName: String = ""
    var fileId: String = ""
    var www: String = ""
    var ctx: String = ""
    var srcPath: String = ""
    var extName : String = ""
    var fileSize: Long = 0
    var mimeType: String = ""
    var createdAt: LocalDateTime = LocalDateTime.now()
    @get:Enumerated(EnumType.STRING)
    var deleteYn: AppConst.YN = AppConst.YN.N
    var deletedAt: LocalDateTime = LocalDateTime.now()

    constructor()
    constructor(fileNo: Long?, fileName: String, fileId: String, www: String, ctx: String, srcPath: String, extName: String, fileSize: Long, mimeType: String) {
        this.fileNo = fileNo
        this.fileName = fileName
        this.fileId = fileId
        this.www = www
        this.ctx = ctx
        this.srcPath = srcPath
        this.extName = extName
        this.fileSize = fileSize
        this.mimeType = mimeType
    }

}