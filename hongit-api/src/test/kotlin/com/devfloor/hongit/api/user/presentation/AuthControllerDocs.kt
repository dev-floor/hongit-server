package com.devfloor.hongit.api.user.presentation

import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.user.application.AuthService
import com.devfloor.hongit.api.user.application.request.JoinRequest
import com.devfloor.hongit.core.user.domain.UserType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ApiDocsTest
internal class AuthControllerDocs {
    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var authService: AuthService

    @InjectMocks
    private lateinit var authController: AuthController

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvc(restDocumentation, authController)
    }

    @Test
    internal fun name() {
        // given
        given(authService.join(JOIN_REQUEST_1)).willReturn(1)

        // when - then
        mockMvc
            .perform(
                post("$BASE_API_URI/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ApiDocsTestUtils.convertAsJson(JOIN_REQUEST_1))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated)
            .andDo(
                MockMvcRestDocumentation.document(
                    "user/join",
                    Preprocessors.preprocessRequest(prettyPrint()),
                    Preprocessors.preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("username").type(JsonFieldType.STRING)
                            .description("사용자의 계정"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING)
                            .description("사용자의 닉네임"),
                        fieldWithPath("password").type(JsonFieldType.STRING)
                            .description("사용자의 비밀번호"),
                        fieldWithPath("checkedPassword").type(JsonFieldType.STRING)
                            .description("사용자의 확인 비밀번호"),
                        fieldWithPath("email").type(JsonFieldType.STRING)
                            .description("사용자의 이메일"),
                        fieldWithPath("type").type(JsonFieldType.STRING)
                            .description("사용자의 유형"),
                        fieldWithPath("classOf").type(JsonFieldType.STRING)
                            .description("사용자의 학번"),
                        fieldWithPath("approved").type(JsonFieldType.BOOLEAN)
                            .description("이메일 인증 여부")
                    ),
                    responseHeaders(
                        headerWithName("Location").description("생성된 사용자 API 호출 URL")
                    )
                )
            )
    }

    companion object {
        private val JOIN_REQUEST_1 = JoinRequest(
            username = "username",
            nickname = "nickname",
            password = "password",
            checkedPassword = "password",
            email = "email@g.hongik.ac.kr",
            type = UserType.STUDENT,
            classOf = "B411158",
            approved = true
        )
    }
}
