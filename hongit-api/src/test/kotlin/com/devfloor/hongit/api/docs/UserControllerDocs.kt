package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.enumFormat
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.PROFILE_RESPONSE_1
import com.devfloor.hongit.api.user.application.UserService
import com.devfloor.hongit.api.user.presentation.UserController
import com.devfloor.hongit.api.user.presentation.UserController.Companion.USER_API_URI
import com.devfloor.hongit.core.user.domain.UserType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
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
    internal fun `showByNickname - 사용자 조회 API 문서화`() {
        // given
        given(userService.showByNickname(anyString())).willReturn(PROFILE_RESPONSE_1)

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get("$USER_API_URI/{nickname}", PROFILE_RESPONSE_1.nickname)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "user/getByNickname",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(
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
