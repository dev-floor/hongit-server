package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.user.application.UserAuthService
import com.devfloor.hongit.api.user.presentation.UserAuthController
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.doNothing
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ApiDocsTest
class UserAuthControllerDocs {
    @InjectMocks
    private lateinit var userAuthController: UserAuthController

    @Mock
    private lateinit var userAuthService: UserAuthService

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvc(restDocumentation, userAuthController)
    }

    @Test
    internal fun `validateUsername - ID(username) 중복검사 API 문서화`() {
        // given
        doNothing().`when`(userAuthService).validateUsername(anyString())

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get("${UserAuthController.AUTH_API_URI}/users")
                    .param("username", "lxxjn0")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "authUser/validateUsername",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.requestParameters(
                        RequestDocumentation.parameterWithName("username").description("중복 확인 ID")
                    )
                )
            )
    }

    @Test
    internal fun `validateNickname - 닉네임 중복검사 API 문서화`() {
        // given
        doNothing().`when`(userAuthService).validateNickname(anyString())

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get("${UserAuthController.AUTH_API_URI}/users")
                    .param("nickname", "lxxjn0")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "authUser/validateNickname",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.requestParameters(
                        RequestDocumentation.parameterWithName("nickname").description("중복 확인 닉네임")
                    )
                )
            )
    }

    @Test
    internal fun `validateClassOf - 학번 중복검사 API 문서화`() {
        // given
        doNothing().`when`(userAuthService).validateClassOf(anyString())

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get("${UserAuthController.AUTH_API_URI}/users")
                    .param("classOf", "B411158")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "authUser/validateClassOf",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.requestParameters(
                        RequestDocumentation.parameterWithName("classOf").description("중복 확인 학번")
                    )
                )
            )
    }
}
