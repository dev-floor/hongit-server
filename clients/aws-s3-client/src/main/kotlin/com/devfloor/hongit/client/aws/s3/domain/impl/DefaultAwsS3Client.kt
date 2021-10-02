package com.devfloor.hongit.client.aws.s3.domain.impl

import com.devfloor.hongit.client.aws.s3.domain.S3DirectoryType
import com.devfloor.hongit.client.aws.s3.domain.spec.AwsS3Client
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.web.multipart.MultipartFile

@Slf4j
class DefaultAwsS3Client : AwsS3Client {
    override fun upload(file: MultipartFile, type: S3DirectoryType): String {
        log.info("[DefaultAwsS3Client.upload] 파일 S3 업로드 완료 - fileName: ${file.name}, type: $type")
        return "default-url/${file.name}"
    }
}
