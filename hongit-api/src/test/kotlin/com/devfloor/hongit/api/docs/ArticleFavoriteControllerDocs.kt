package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.articlefavorite.application.ArticleFavoriteService
import com.devfloor.hongit.api.articlefavorite.presentation.ArticleFavoriteController
import com.devfloor.hongit.api.articlefavorite.presentation.ArticleFavoriteController.Companion.ARTICLE_FAVORITE_API_URI
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.authorizationFormat
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.api.support.TestFixtures.ArticleFavoriteFixture.ARTICLE_FAVORITE_CREATE_REQUEST_1
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.USER_1
import com.devfloor.hongit.core.user.domain.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willDoNothing
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ApiDocsTest
internal class ArticleFavoriteControllerDocs {
    @InjectMocks
    private lateinit var articleFavoriteController: ArticleFavoriteController

    @Mock
    private lateinit var articleFavoriteService: ArticleFavoriteService

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvcWithLoginUser(
            restDocumentation,
            articleFavoriteController,
            userRepository,
        )
    }

    @Test
    internal fun `create - 게시글 좋아요 생성 문서화`() {
        given(articleFavoriteService.create(any(), any())).willReturn(1)
        given(userRepository.findAll()).willReturn(listOf(USER_1))
        val request: String = ApiDocsTestUtils.convertAsJson(ARTICLE_FAVORITE_CREATE_REQUEST_1)

        mockMvc
            .perform(
                post(ARTICLE_FAVORITE_API_URI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated)
            .andDo(
                document(
                    "articleFavorite/create",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(로그인시 발급되는) 인증 토큰")
                    ),
                    requestFields(
                        fieldWithPath("articleId").type(JsonFieldType.NUMBER)
                            .description("게시글 ID"),
                        fieldWithPath("type").type(JsonFieldType.STRING)
                            .description("게시글 좋아요 type"),
                    ),
                )
            )
    }

    @Test
    internal fun `destroy - 게시글 좋아요 삭제 문서화`() {
        willDoNothing().given(articleFavoriteService).destroy(anyLong(), any())
        given(userRepository.findAll()).willReturn(listOf(USER_1))

        mockMvc
            .perform(
                delete(ARTICLE_FAVORITE_API_URI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .param("articleId", "1")
            )
            .andExpect(status().isNoContent)
            .andDo(
                document(
                    "articleFavorite/destroy",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(로그인시 발급되는) 인증 토큰")
                    ),
                    requestParameters(
                        parameterWithName("articleId").description("좋아요 삭제 할 게시글 ID")
                    ),
                )
            )
    }
}
