package com.devfloor.hongit.client.aws.s3.domain.impl

import com.devfloor.hongit.client.aws.s3.common.config.AwsS3ClientProperties
import com.devfloor.hongit.client.aws.s3.common.utils.AwsS3Utils
import com.devfloor.hongit.client.aws.s3.domain.S3DirectoryType
import com.devfloor.hongit.client.aws.s3.domain.spec.AwsS3Client
import com.devfloor.hongit.client.aws.s3.exception.AwsS3ClientException
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ObjectCannedACL

@Slf4j
class ProdAwsS3Client(
    private val s3Client: S3Client,
    private val properties: AwsS3ClientProperties,
) : AwsS3Client {
    override fun upload(file: MultipartFile, type: S3DirectoryType): String {
        val uploadFile = AwsS3Utils.convertMultipartFileToFile(file)
        val key = AwsS3Utils.createKey(uploadFile, type)

        try {
            s3Client.putObject({ it.bucket(properties.bucket).key(key).acl(ObjectCannedACL.PUBLIC_READ) },
                uploadFile.toPath())
                .also { if (!it.sdkHttpResponse().isSuccessful) throw AwsS3ClientException("S3 업로드에 실패하였습니다") }

            return s3Client.utilities()
                .getUrl { it.bucket(properties.bucket).key(key) }
                .toString()
        } catch (e: AwsS3ClientException) {
            log.error("[ProdAwsS3Client.upload] S3 파일 업로드에 실패하였습니다 - key: $key")
            throw AwsS3ClientException(e.message)
        } catch (e: SdkException) {
            log.error("[ProdAwsS3Client.upload] AWS S3 SDK 오류가 발생하였습니다 - key: $key, message: ${e.message}")
            throw AwsS3ClientException("AWS S3 SDK 오류가 발생하였습니다 - message: ${e.message}")
        } finally {
            uploadFile.delete()
        }
    }
}
