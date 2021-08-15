package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.customFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.emptyFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.required
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.JOIN_REQUEST_1
import com.devfloor.hongit.api.user.application.AuthService
import com.devfloor.hongit.api.user.presentation.AuthController
import com.devfloor.hongit.core.user.domain.Email
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ApiDocsTest
internal class AuthControllerDocs {
    @InjectMocks
    private lateinit var authController: AuthController

    @Mock
    private lateinit var authService: AuthService

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvc(restDocumentation, authController)
    }

    @Test
    internal fun `join - 회원가입 문서화 테스트`() {
        // given
        given(authService.join(JOIN_REQUEST_1)).willReturn(1)

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.post("$BASE_API_URI/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ApiDocsTestUtils.convertAsJson(JOIN_REQUEST_1))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated)
            .andDo(
                MockMvcRestDocumentation.document(
                    "user/join",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("username").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), required())
                            .description("사용자 계정"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), required())
                            .description("사용자 닉네임"),
                        fieldWithPath("password").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), required())
                            .description("사용자 비밀번호"),
                        fieldWithPath("checkedPassword").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), required())
                            .description("사용자 비밀번호 확인"),
                        fieldWithPath("email").type(JsonFieldType.STRING)
                            .attributes(customFormat(Email.VALID_DOMAINS), required())
                            .description("사용자 이메일"),
                        fieldWithPath("type").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), required())
                            .description("사용자 유형"),
                        fieldWithPath("classOf").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), required())
                            .description("사용자 학번"),
                        fieldWithPath("approved").type(JsonFieldType.BOOLEAN)
                            .attributes(emptyFormat(), required())
                            .description("이메일 인증 여부")
                    ),
                    HeaderDocumentation.responseHeaders(
                        headerWithName("Location").description("생성된 사용자 상세조회 API")
                    )
                )
            )
    }
}
