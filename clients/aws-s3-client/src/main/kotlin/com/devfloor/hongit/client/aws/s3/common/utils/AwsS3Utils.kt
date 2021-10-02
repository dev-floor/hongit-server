package com.devfloor.hongit.client.aws.s3.common.utils

import com.devfloor.hongit.client.aws.s3.domain.S3DirectoryType
import com.devfloor.hongit.client.aws.s3.exception.AwsS3ClientException
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Slf4j
object AwsS3Utils {
    fun convertMultipartFileToFile(multipartFile: MultipartFile): File {
        val file = multipartFile.originalFilename
            ?.let { File(it) }
            ?: throw AwsS3ClientException("MutlipartFile이 유효하지 않습니다")
                .also { log.error("[AwsS3Utils.convertMultipartFileToFile] MutlipartFile이 유효하지 않습니다") }

        return try {
            if (file.createNewFile()) {
                file.also { FileOutputStream(it).write(multipartFile.bytes) }
            } else {
                throw IOException("fileName: ${file.name}")
            }
        } catch (e: IOException) {
            log.error("[AwsS3Utils.convertMultipartFileToFile] 파일 생성에 실패하였습니다 - message: ${e.message}")
            throw AwsS3ClientException(e.message ?: "파일 생성에 실패하였습니다")
        }
    }

    fun createKey(file: File, type: S3DirectoryType): String {
        val uploadDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        return "${type.text}/$uploadDate/${file.name}"
    }
}
