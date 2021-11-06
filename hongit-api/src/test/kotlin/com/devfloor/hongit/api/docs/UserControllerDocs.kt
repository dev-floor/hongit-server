package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.security.web.AuthorizationType
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.enumFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.format
import com.devfloor.hongit.api.support.TestFixtures
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.JOIN_REQUEST_1
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.LOGIN_REQUEST_1
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.PROFILE_RESPONSE_1
import com.devfloor.hongit.api.user.application.UserService
import com.devfloor.hongit.api.user.application.response.TokenResponse
import com.devfloor.hongit.api.user.presentation.UserController
import com.devfloor.hongit.api.user.presentation.UserController.Companion.LOGIN_API_URI
import com.devfloor.hongit.api.user.presentation.UserController.Companion.SIGNUP_API_URI
import com.devfloor.hongit.api.user.presentation.UserController.Companion.USER_API_URI
import com.devfloor.hongit.core.user.domain.Email
import com.devfloor.hongit.core.user.domain.UserType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.headers.HeaderDocumentation
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
internal class UserControllerDocs {
    @InjectMocks
    private lateinit var userController: UserController

    @Mock
    private lateinit var userService: UserService

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvc(restDocumentation, userController)
    }

    @Test
    internal fun `signUp - 회원가입 API 문서화`() {
        // given
        given(userService.signUp(JOIN_REQUEST_1)).willReturn(1)

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.post(SIGNUP_API_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ApiDocsTestUtils.convertAsJson(TestFixtures.UserFixture.JOIN_REQUEST_1))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated)
            .andDo(
                MockMvcRestDocumentation.document(
                    "user/signup",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("username").type(JsonFieldType.STRING)
                            .description("사용자 계정"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING)
                            .description("사용자 닉네임"),
                        fieldWithPath("password").type(JsonFieldType.STRING)
                            .description("사용자 비밀번호"),
                        fieldWithPath("checkedPassword").type(JsonFieldType.STRING)
                            .description("사용자 비밀번호 확인"),
                        fieldWithPath("email").type(JsonFieldType.STRING)
                            .format(Email.VALID_DOMAINS.map { "@$it" })
                            .description("사용자 이메일"),
                        fieldWithPath("type").type(JsonFieldType.STRING)
                            .enumFormat(UserType::class)
                            .description("사용자 유형"),
                        fieldWithPath("classOf").type(JsonFieldType.STRING)
                            .description("사용자 학번"),
                        fieldWithPath("approved").type(JsonFieldType.BOOLEAN)
                            .description("이메일 인증 여부")
                    ),
                    HeaderDocumentation.responseHeaders(
                        HeaderDocumentation.headerWithName("Location").description("생성된 사용자 상세조회 API")
                    )
                )
            )
    }

    @Test
    internal fun `login - 로그인 API 문서화`() {
        // given
        given(userService.login(LOGIN_REQUEST_1)).willReturn(TokenResponse("token", AuthorizationType.BEARER))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.post(LOGIN_API_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ApiDocsTestUtils.convertAsJson(LOGIN_REQUEST_1))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "user/login",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("username").type(JsonFieldType.STRING)
                            .description("사용자 계정"),
                        fieldWithPath("password").type(JsonFieldType.STRING)
                            .description("사용자 비밀번호"),
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("token").type(JsonFieldType.STRING)
                            .description("생성된 JWT 토큰"),
                        fieldWithPath("type").type(JsonFieldType.STRING)
                            .enumFormat(AuthorizationType::class)
                            .description("생성된 JWT 토큰 타입")
                    )
                )
            )
    }

    @Test
    internal fun `showByNickname - 사용자 조회 API 문서화`() {
        // given
        given(userService.showByNickname(anyString())).willReturn(PROFILE_RESPONSE_1)

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get(USER_API_URI)
                    .param("nickname", PROFILE_RESPONSE_1.nickname)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "user/getByNickname",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.requestParameters(
                        parameterWithName("nickname").description("조회 닉네임")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                        fieldWithPath("type.id").type(JsonFieldType.STRING)
                            .enumFormat(UserType::class)
                            .description("사용자 유형"),
                        fieldWithPath("type.text").type(JsonFieldType.STRING)
                            .description("사용자 유형 설명"),
                        fieldWithPath("image").type(JsonFieldType.STRING)
                            .optional()
                            .description("프로필 사진 URL"),
                        fieldWithPath("github").type(JsonFieldType.STRING)
                            .optional()
                            .description("Github 계정"),
                        fieldWithPath("blog").type(JsonFieldType.STRING)
                            .optional()
                            .description("블로그 주소"),
                        fieldWithPath("description").type(JsonFieldType.STRING)
                            .optional()
                            .description("프로필 소개")
                    )
                )
            )
    }
}
