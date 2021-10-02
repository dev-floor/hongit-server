package com.devfloor.hongit.client.aws.s3.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "aws.s3")
class AwsS3ClientProperties(
    val bucket: String,
    val accessKey: String,
    val secretKey: String,
)
