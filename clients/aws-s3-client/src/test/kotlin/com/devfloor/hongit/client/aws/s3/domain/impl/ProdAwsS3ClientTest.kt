package com.devfloor.hongit.client.aws.s3.domain.impl

import com.devfloor.hongit.client.aws.s3.common.config.AwsS3ClientConfig
import com.devfloor.hongit.client.aws.s3.domain.S3DirectoryType
import com.devfloor.hongit.client.aws.s3.domain.spec.AwsS3Client
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@Disabled
@ActiveProfiles(value = ["prod"])
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [AwsS3ClientConfig::class])
internal class ProdAwsS3ClientTest {
    @Autowired
    private lateinit var awsS3Client: AwsS3Client

    @Test
    internal fun `upload - S3 파일 업로드`() {
        // given
        val text = "S3 upload test file."
        val file = MockMultipartFile("test", "test.txt", "text/plain", text.toByteArray())

        // when
        awsS3Client.upload(file, S3DirectoryType.ARTICLE).also { println(it) }
    }
}
