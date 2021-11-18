package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.commentfavorite.application.CommentFavoriteService
import com.devfloor.hongit.api.commentfavorite.presentation.CommentFavoriteController
import com.devfloor.hongit.api.commentfavorite.presentation.CommentFavoriteController.Companion.COMMENT_FAVORITE_API_URI
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.authorizationFormat
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.api.support.TestFixtures.CommentFavoriteFixture.COMMENT_FAVORITE_1
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.USER_1
import com.devfloor.hongit.core.user.domain.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyLong
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willDoNothing
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ApiDocsTest
internal class CommentFavoriteControllerDocs {
    @InjectMocks
    private lateinit var commentFavoriteController: CommentFavoriteController

    @Mock
    private lateinit var commentFavoriteService: CommentFavoriteService

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvcWithLoginUser(
            restDocumentation,
            commentFavoriteController,
            userRepository,
        )
    }

    @Test
    internal fun `create - 댓글 좋아요 생성 문서화`() {
        given(commentFavoriteService.create(anyLong(), any())).willReturn(COMMENT_FAVORITE_1.id)
        given(userRepository.findAll()).willReturn(listOf(USER_1))

        mockMvc
            .perform(
                post(COMMENT_FAVORITE_API_URI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .param("commentId", "1")
            )
            .andExpect(status().isCreated)
            .andDo(
                document(
                    "commentFavorite/create",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(로그인시 발급되는) 인증 토큰")
                    ),
                    requestParameters(
                        parameterWithName("commentId").description("좋아요 생성 할 댓글 ID")
                    )
                )
            )
    }

    @Test
    internal fun `destroy - 댓글 좋아요 삭제 문서화`() {
        willDoNothing().given(commentFavoriteService).destroy(anyLong(), any())
        given(userRepository.findAll()).willReturn(listOf(USER_1))

        mockMvc
            .perform(
                delete(COMMENT_FAVORITE_API_URI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .param("commentId", "1")
            )
            .andExpect(status().isNoContent)
            .andDo(
                document(
                    "commentFavorite/delete",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(로그인시 발급되는) 인증 토큰")
                    ),
                    requestParameters(
                        parameterWithName("commentId").description("좋아요 삭제 할 댓글 ID")
                    )
                )
            )
    }
}
