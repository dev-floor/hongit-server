package com.devfloor.hongit.client.aws.s3.common.config

import com.devfloor.hongit.client.aws.s3.domain.impl.DefaultAwsS3Client
import com.devfloor.hongit.client.aws.s3.domain.impl.ProdAwsS3Client
import com.devfloor.hongit.client.aws.s3.domain.spec.AwsS3Client
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
@EnableConfigurationProperties(value = [AwsS3ClientProperties::class])
class AwsS3ClientConfig(
    private val properties: AwsS3ClientProperties,
) {
    @Bean
    @ConditionalOnProperty(value = [AWS_S3_MODE], havingValue = "stub")
    fun defaultAwsS3Client(): AwsS3Client = DefaultAwsS3Client()

    @Bean
    @ConditionalOnProperty(value = [AWS_S3_MODE], havingValue = "prod")
    fun prodAwsS3Client(s3Client: S3Client, properties: AwsS3ClientProperties): AwsS3Client =
        ProdAwsS3Client(s3Client, properties)

    @Bean
    @ConditionalOnProperty(value = [AWS_S3_MODE], havingValue = "prod")
    fun s3Client(): S3Client = S3Client.builder()
        .region(Region.AP_NORTHEAST_2)
        .credentialsProvider(credentialsProvider())
        .build()

    private fun credentialsProvider(): AwsCredentialsProvider = StaticCredentialsProvider.create(
        AwsBasicCredentials.create(properties.accessKey, properties.secretKey)
    )

    companion object {
        private const val AWS_S3_MODE = "aws.s3.mode"
    }
}
