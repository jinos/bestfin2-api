package com.fin.best.bestfin.api.component.model

import java.time.LocalDateTime

data class FileVo(
        var fileName: String,
        var fileId: String,
        var srcPath: String,
        var extName: String,
        var fileSize: Long,
        var mime: String
) {
    var fileNo: Long? = null
    var createdAt: LocalDateTime = LocalDateTime.now()
}