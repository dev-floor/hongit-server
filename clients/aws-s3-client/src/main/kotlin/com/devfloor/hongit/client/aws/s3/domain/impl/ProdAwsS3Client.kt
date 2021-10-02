package com.devfloor.hongit.client.aws.s3.domain.impl

import com.devfloor.hongit.client.aws.s3.common.config.AwsS3ClientProperties
import com.devfloor.hongit.client.aws.s3.domain.S3DirectoryType
import com.devfloor.hongit.client.aws.s3.domain.spec.AwsS3Client
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.services.s3.S3Client

class ProdAwsS3Client(
    private val s3Client: S3Client,
    private val properties: AwsS3ClientProperties,
) : AwsS3Client {
    override fun upload(file: MultipartFile, type: S3DirectoryType): String {
        TODO("Not yet implemented")
    }
}
