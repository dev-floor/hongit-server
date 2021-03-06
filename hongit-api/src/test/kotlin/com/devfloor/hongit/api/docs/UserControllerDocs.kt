package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.security.web.AuthorizationType
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.authorizationFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.enumFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.format
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.DESTROY_REQUEST
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.JOIN_REQUEST_1
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.LOGIN_REQUEST_1
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.PASSWORD_MODIFY_REQUEST
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.PROFILE_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.USER_1
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.USER_MODIFY_REQUEST_1
import com.devfloor.hongit.api.user.application.UserService
import com.devfloor.hongit.api.user.application.response.TokenResponse
import com.devfloor.hongit.api.user.presentation.UserController
import com.devfloor.hongit.api.user.presentation.UserController.Companion.LOGIN_API_URI
import com.devfloor.hongit.api.user.presentation.UserController.Companion.ME_API
import com.devfloor.hongit.api.user.presentation.UserController.Companion.SIGNUP_API_URI
import com.devfloor.hongit.api.user.presentation.UserController.Companion.USER_API_URI
import com.devfloor.hongit.core.user.domain.Email
import com.devfloor.hongit.core.user.domain.UserRepository
import com.devfloor.hongit.core.user.domain.UserType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willDoNothing
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ApiDocsTest
internal class UserControllerDocs {
    @InjectMocks
    private lateinit var userController: UserController

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvcWithLoginUser(restDocumentation, userController, userRepository)
    }

    @Test
    internal fun `signUp - ???????????? API ?????????`() {
        // given
        given(userService.signUp(JOIN_REQUEST_1)).willReturn(1)

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.post(SIGNUP_API_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ApiDocsTestUtils.convertAsJson(JOIN_REQUEST_1))
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
                            .description("????????? ??????"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING)
                            .description("????????? ?????????"),
                        fieldWithPath("password").type(JsonFieldType.STRING)
                            .description("????????? ????????????"),
                        fieldWithPath("checkedPassword").type(JsonFieldType.STRING)
                            .description("????????? ???????????? ??????"),
                        fieldWithPath("email").type(JsonFieldType.STRING)
                            .format(Email.VALID_DOMAINS.map { "@$it" })
                            .description("????????? ?????????"),
                        fieldWithPath("type").type(JsonFieldType.STRING)
                            .enumFormat(UserType::class)
                            .description("????????? ??????"),
                        fieldWithPath("classOf").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("approved").type(JsonFieldType.BOOLEAN)
                            .description("????????? ?????? ??????")
                    ),
                    HeaderDocumentation.responseHeaders(
                        headerWithName("Location").description("????????? ????????? ???????????? API")
                    )
                )
            )
    }

    @Test
    internal fun `login - ????????? API ?????????`() {
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
                            .description("????????? ??????"),
                        fieldWithPath("password").type(JsonFieldType.STRING)
                            .description("????????? ????????????"),
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("token").type(JsonFieldType.STRING)
                            .description("????????? JWT ??????"),
                        fieldWithPath("type").type(JsonFieldType.STRING)
                            .enumFormat(AuthorizationType::class)
                            .description("????????? JWT ?????? ??????")
                    )
                )
            )
    }

    @Test
    internal fun `showByNickname - ????????? ?????? API ?????????`() {
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
                        parameterWithName("nickname").description("?????? ?????????")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("?????????"),
                        fieldWithPath("type.id").type(JsonFieldType.STRING)
                            .enumFormat(UserType::class)
                            .description("????????? ??????"),
                        fieldWithPath("type.text").type(JsonFieldType.STRING)
                            .description("????????? ?????? ??????"),
                        fieldWithPath("image").type(JsonFieldType.STRING)
                            .optional()
                            .description("????????? ?????? URL"),
                        fieldWithPath("github").type(JsonFieldType.STRING)
                            .optional()
                            .description("Github ??????"),
                        fieldWithPath("blog").type(JsonFieldType.STRING)
                            .optional()
                            .description("????????? ??????"),
                        fieldWithPath("description").type(JsonFieldType.STRING)
                            .optional()
                            .description("????????? ??????")
                    )
                )
            )
    }

    @Test
    internal fun `modifyUser - ????????? ?????? API ?????????`() {
        // given
        willDoNothing().given(userService).modifyUser(any(), any())
        given(userRepository.findAll()).willReturn(listOf(USER_1))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.put("$BASE_API_URI/me")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ApiDocsTestUtils.convertAsJson(USER_MODIFY_REQUEST_1))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent)
            .andDo(
                MockMvcRestDocumentation.document(
                    "user/putUser",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    HeaderDocumentation.requestHeaders(
                        HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(???????????? ????????????) ?????? ??????")
                    ),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("nickname").type(JsonFieldType.STRING)
                            .description("?????????"),
                        fieldWithPath("userType").type(JsonFieldType.STRING)
                            .description("?????? ??????"),
                        fieldWithPath("image").type(JsonFieldType.STRING)
                            .description("????????? URL"),
                        fieldWithPath("github").type(JsonFieldType.STRING)
                            .description("github ??????"),
                        fieldWithPath("blog").type(JsonFieldType.STRING)
                            .description("blog ??????"),
                        fieldWithPath("description").type(JsonFieldType.STRING)
                            .description("??????"),
                    ),
                )
            )
    }

    @Test
    internal fun `modifyPassword - ?????? ???????????? ?????? API ?????????`() {
        // given
        willDoNothing().given(userService).modifyPassword(any(), any())
        given(userRepository.findAll()).willReturn(listOf(USER_1))
        val request: String = ApiDocsTestUtils.convertAsJson(PASSWORD_MODIFY_REQUEST)

        // when - then
        mockMvc
            .perform(
                patch(ME_API)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request)
            )
            .andExpect(status().isNoContent)
            .andDo(
                MockMvcRestDocumentation.document(
                    "user/modifyPassword",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(???????????? ????????????) ?????? ??????")
                    ),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("oldPassword").type(JsonFieldType.STRING).description("?????? ????????????"),
                        fieldWithPath("newPassword").type(JsonFieldType.STRING).description("????????? ????????????"),
                        fieldWithPath("checkedNewPassword").type(JsonFieldType.STRING)
                            .description("????????? ???????????? ??????"),
                    )
                )
            )
    }

    @Test
    internal fun `destroy - ???????????? API ?????????`() {
        // given
        willDoNothing().given(userService).destroy(any(), any())
        given(userRepository.findAll()).willReturn(listOf(USER_1))
        val request: String = ApiDocsTestUtils.convertAsJson(DESTROY_REQUEST)

        // when - then
        mockMvc
            .perform(
                delete(ME_API)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request)
            )
            .andExpect(status().isNoContent)
            .andDo(
                MockMvcRestDocumentation.document(
                    "user/delete",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(???????????? ????????????) ?????? ??????")
                    ),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????????"),
                    )
                )
            )
    }
}
