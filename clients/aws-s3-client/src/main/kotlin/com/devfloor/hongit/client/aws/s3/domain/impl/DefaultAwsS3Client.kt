package com.devfloor.hongit.client.aws.s3.domain.impl

import com.devfloor.hongit.client.aws.s3.domain.S3DirectoryType
import com.devfloor.hongit.client.aws.s3.domain.spec.AwsS3Client
import org.springframework.web.multipart.MultipartFile

class DefaultAwsS3Client : AwsS3Client {
    override fun upload(file: MultipartFile, type: S3DirectoryType): String {
        TODO("Not yet implemented")
    }
}
