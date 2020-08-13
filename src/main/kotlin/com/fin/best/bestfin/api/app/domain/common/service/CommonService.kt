package com.fin.best.bestfin.api.app.domain.common.service

import com.fin.best.bestfin.api.app.domain.common.dao.CommonDao
import com.fin.best.bestfin.api.app.domain.common.entity.CommonFiles
import com.fin.best.bestfin.api.component.error.exception.FailUploadFileHandleException
import com.fin.best.bestfin.api.component.model.FileVo
import com.fin.best.bestfin.api.component.utils.FileHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Service
class CommonService
@Autowired constructor(
        private val commonDao: CommonDao
) {
    private val logger: Logger = LoggerFactory.getLogger(CommonService::class.java)

    fun storeCommonFiles(fileVo: FileVo): CommonFiles {
        return commonDao.storeCommonFiles(
                CommonFiles(
                        fileNo = fileVo.fileNo,
                        fileName = fileVo.fileName,
                        fileId = fileVo.fileId,
                        www = "",
                        ctx = "",
                        srcPath = fileVo.srcPath,
                        extName = fileVo.extName,
                        fileSize = fileVo.fileSize,
                        mimeType = fileVo.mime
                )
        )
    }
}
