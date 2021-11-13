package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.common.presentation.CommonController
import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.enumFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.format
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.client.aws.s3.domain.S3DirectoryType
import com.devfloor.hongit.client.aws.s3.domain.spec.AwsS3Client
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.partWithName
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ApiDocsTest
class CommonControllerDocs {
    @InjectMocks
    private lateinit var commonController: CommonController

    @Mock
    private lateinit var awsS3Client: AwsS3Client

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvc(restDocumentation, commonController)
    }

    @Test
    internal fun `uploadImage - 이미지 업로드 문서화`() {
        // given
        given(awsS3Client.upload(any(), any())).willReturn("https://image-url.jpeg")
        val image = MockMultipartFile("image", "image.jpeg", "image/jpg", "test jpeg".toByteArray())

        // when - then
        mockMvc
            .perform(
                MockMvcRequestBuilders.multipart("$BASE_API_URI/images")
                    .file(image)
                    .param("type", S3DirectoryType.ARTICLE.name)
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "common/uploadImage",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.requestParts(
                        partWithName("image").format("multipart-file")
                            .description("업로드할 이미지")
                    ),
                    RequestDocumentation.requestParameters(
                        parameterWithName("type").enumFormat(S3DirectoryType::class)
                            .description("저장할 파일 디렉토리(유형)")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("url").type(JsonFieldType.STRING)
                            .description("이미지 Url")
                    )
                )
            )
    }
}
