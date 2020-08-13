package com.fin.best.bestfin.api.component.utils

import com.fin.best.bestfin.api.component.model.FileVo
import com.fin.best.bestfin.api.component.constants.AppConst
import org.apache.commons.io.FileUtils
import org.apache.tika.Tika
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.*

class FileHandler {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(FileHandler::class.java)

        fun transferFiles(multipartFile: MultipartFile, file: File): Boolean {
            try {
                multipartFile.transferTo(file)
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }
            return true
        }

        fun generateFileOrigin(originFileName: String, fileId: String, extName: String, file: File): FileVo {
            logger.info("String originFileName=>{}, String fileId=>{}, String extName=>{}", originFileName, fileId, extName)
            return FileVo(
                    fileName = originFileName,
                    fileId = fileId,
                    srcPath = file.path,
                    extName = extName,
                    fileSize = file.length(),
                    mime = readMimeType((file))
            )
        }

        fun getFile(filePath: String): Optional<File> {
            val file = File(filePath)
            return if (file.exists()) Optional.of(file) else Optional.empty()
        }

        fun getFileResource(filePath: String): FileSystemResource? {
            val file = File(filePath)
            return if (file.exists()) FileSystemResource(filePath) else null
        }

        fun removeFile(filePath: String?): Boolean {
            val file = File(filePath)
            if (file.exists()) {
                try {
                    FileUtils.forceDelete(file)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return true
        }

        private fun readMimeType(file: File): String {
            var mimeType: String
            return try {
                Tika().detect(file)
            } catch (e: IOException) {
                e.printStackTrace()
                try {
                    Files.probeContentType(file.toPath())
                } catch (e1: IOException) {
                    e1.printStackTrace()
                    AppConst.File.DefaultSystemFileMime
                }
            }
        }
    }
}