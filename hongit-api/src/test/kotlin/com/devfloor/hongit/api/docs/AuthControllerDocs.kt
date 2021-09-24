package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.auth.application.AuthService
import com.devfloor.hongit.api.auth.application.request.AuthMailRequest
import com.devfloor.hongit.api.auth.presentation.AuthController
import com.devfloor.hongit.api.auth.presentation.AuthController.Companion.AUTH_API_URI
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.format
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.api.support.TestFixtures.AuthTokenFixture.AUTH_TOKEN_1
import com.devfloor.hongit.core.user.domain.Email
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.willDoNothing
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ApiDocsTest
class AuthControllerDocs {
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
    internal fun `sendAuthenticationMail - 인증메일 발송 API 문서화`() {
        // given
        willDoNothing().given(authService).sendAuthenticationMail(any())

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.post(AUTH_API_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ApiDocsTestUtils.convertAsJson(AuthMailRequest("test@g.hongik.ac.kr")))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent)
            .andDo(
                MockMvcRestDocumentation.document(
                    "auth/postAuth",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("receiverEmail").type(JsonFieldType.STRING)
                            .format(Email.VALID_DOMAINS.map { "@$it" })
                            .description("인증 메일 수신 이메일")
                    )
                )
            )
    }

    @Test
    internal fun `validateAuthToken - 인증토큰 유효성 검사 API 문서화`() {
        // given
        willDoNothing().given(authService).validateAuthToken(anyString())

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.put("$AUTH_API_URI/tokens/{tokenId}", AUTH_TOKEN_1.id.toString())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent)
            .andDo(
                MockMvcRestDocumentation.document(
                    "auth/getValidateAuthToken",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(
                        parameterWithName("tokenId").description("인증토큰")
                    )
                )
            )
    }
}
