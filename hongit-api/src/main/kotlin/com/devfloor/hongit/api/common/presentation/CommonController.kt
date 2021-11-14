package com.devfloor.hongit.api.common.presentation

import com.devfloor.hongit.api.common.application.response.ImageUploadResponse
import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.client.aws.s3.domain.S3DirectoryType
import com.devfloor.hongit.client.aws.s3.domain.spec.AwsS3Client
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Slf4j
@RestController
@RequestMapping(value = [BASE_API_URI])
class CommonController(
    private val awsS3Client: AwsS3Client,
) {
    @PostMapping(value = ["/images"])
    @ResponseStatus(value = HttpStatus.OK)
    fun uploadImage(@RequestParam image: MultipartFile, @RequestParam type: S3DirectoryType): ImageUploadResponse =
        ImageUploadResponse(awsS3Client.upload(image, type))
            .also { log.info("[CommonController.uploadImage] 이미지 업로드 완료 - url: {}", it.url) }
}
