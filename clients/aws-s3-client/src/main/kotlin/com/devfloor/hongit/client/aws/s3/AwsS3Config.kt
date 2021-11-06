package com.devfloor.hongit.client.aws.s3

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration

@Configuration
@EntityScan(basePackages = ["com.devfloor.hongit.client.aws.s3"])
class AwsS3Config
