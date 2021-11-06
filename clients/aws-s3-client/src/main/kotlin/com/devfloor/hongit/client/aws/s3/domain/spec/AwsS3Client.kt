package com.devfloor.hongit.client.aws.s3.domain.spec

import com.devfloor.hongit.client.aws.s3.domain.S3DirectoryType
import org.springframework.web.multipart.MultipartFile

interface AwsS3Client {
    fun upload(file: MultipartFile, type: S3DirectoryType): String
}
