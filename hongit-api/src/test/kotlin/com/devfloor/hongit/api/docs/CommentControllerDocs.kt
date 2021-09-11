package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.comment.application.CommentService
import com.devfloor.hongit.api.comment.presentation.CommentController
import com.devfloor.hongit.api.comment.presentation.CommentController.Companion.COMMENT_API_URI
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.authorizationFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.dateTimeFormat
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.api.support.TestFixtures.CommentFixture.COMMENT_CREATE_REQUEST_1
import com.devfloor.hongit.api.support.TestFixtures.CommentFixture.COMMENT_IN_PROFILE_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.CommentFixture.COMMENT_MODIFY_REQUEST_1
import com.devfloor.hongit.api.support.TestFixtures.CommentFixture.COMMENT_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.USER_1
import com.devfloor.hongit.core.user.domain.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyLong
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willDoNothing
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.HttpHeaders
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
internal class CommentControllerDocs {
    @InjectMocks
    private lateinit var commentController: CommentController

    @Mock
    private lateinit var commentService: CommentService

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvcWithLoginUser(restDocumentation, commentController, userRepository)
    }

    @Test
    internal fun `showAllByArticleId - 특정 게시글 댓글 목록 조회 API 문서화`() {
        // given
        given(commentService.showAllByArticleId(anyLong())).willReturn(listOf(COMMENT_RESPONSE_1))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get(COMMENT_API_URI)
                    .param("articleId", "1")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "comment/getAllByArticleId",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.requestParameters(
                        parameterWithName("articleId").description("특정 게시글의 ID")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .description("댓글 ID"),
                        fieldWithPath("[].authorName").type(JsonFieldType.STRING)
                            .description("댓글 작성자 닉네임"),
                        fieldWithPath("[].anonymous").type(JsonFieldType.BOOLEAN)
                            .description("댓글 익명 여부"),
                        fieldWithPath("[].content").type(JsonFieldType.STRING)
                            .description("댓글 내용"),
                        fieldWithPath("[].favoriteCount").type(JsonFieldType.NUMBER)
                            .description("댓글 좋아요 개수"),
                        fieldWithPath("[].createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("댓글 생성 시간"),
                        fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("댓글 수정 시간")
                    )
                )
            )
    }

    @Test
    internal fun `showAllByNickname - 특정 회원 댓글 목록 조회 API 문서화`() {
        // given
        given(commentService.showAllByNickname(anyString())).willReturn(listOf(COMMENT_IN_PROFILE_RESPONSE_1))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get(COMMENT_API_URI)
                    .param("nickname", "nickname")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "comment/getAllByNickname",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.requestParameters(
                        parameterWithName("nickname").description("특정 회원의 닉네임")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].comment.id").type(JsonFieldType.NUMBER)
                            .description("댓글 ID"),
                        fieldWithPath("[].comment.authorName").type(JsonFieldType.STRING)
                            .description("댓글 작성자 닉네임"),
                        fieldWithPath("[].comment.anonymous").type(JsonFieldType.BOOLEAN)
                            .description("댓글 익명 여부"),
                        fieldWithPath("[].comment.content").type(JsonFieldType.STRING)
                            .description("댓글 내용"),
                        fieldWithPath("[].comment.favoriteCount").type(JsonFieldType.NUMBER)
                            .description("댓글 좋아요 개수"),
                        fieldWithPath("[].comment.createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("댓글 생성 시간"),
                        fieldWithPath("[].comment.modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("댓글 수정 시간"),
                        fieldWithPath("[].articleId").type(JsonFieldType.NUMBER)
                            .description("댓글 작성 게시글 ID"),
                        fieldWithPath("[].articleTitle").type(JsonFieldType.STRING)
                            .optional()
                            .description("댓글 작성 게시글 제목"),
                    )
                )
            )
    }

    @Test
    internal fun `create - 댓글 생성 API 문서화`() {
        // given
        given(commentService.create(any(), any())).willReturn(COMMENT_RESPONSE_1)
        given(userRepository.findAll()).willReturn(listOf(USER_1))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.post(COMMENT_API_URI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ApiDocsTestUtils.convertAsJson(COMMENT_CREATE_REQUEST_1))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated)
            .andDo(
                MockMvcRestDocumentation.document(
                    "comment/post",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    HeaderDocumentation.requestHeaders(
                        HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(로그인시 발급되는) 인증 토큰")
                    ),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("articleId").type(JsonFieldType.NUMBER)
                            .description("댓글 작성 게시글 ID"),
                        fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN)
                            .description("댓글 익명 여부"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("댓글 내용"),
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER)
                            .description("댓글 ID"),
                        fieldWithPath("authorName").type(JsonFieldType.STRING)
                            .description("댓글 작성자 닉네임"),
                        fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN)
                            .description("댓글 익명 여부"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("댓글 내용"),
                        fieldWithPath("favoriteCount").type(JsonFieldType.NUMBER)
                            .description("댓글 좋아요 개수"),
                        fieldWithPath("createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("댓글 생성 시간"),
                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("댓글 수정 시간")
                    )
                )
            )
    }

    @Test
    internal fun `modifyByCommentId - 댓글 수정 API 문서화`() {
        // given
        given(commentService.modifyByCommentId(anyLong(), any())).willReturn(COMMENT_RESPONSE_1)
        given(userRepository.findAll()).willReturn(listOf(USER_1))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.put("$COMMENT_API_URI/{commentId}", COMMENT_RESPONSE_1.id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ApiDocsTestUtils.convertAsJson(COMMENT_MODIFY_REQUEST_1))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "comment/put",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(
                        parameterWithName("commentId").description("수정할 댓글 ID")
                    ),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("수정 댓글 내용"),
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER)
                            .description("댓글 ID"),
                        fieldWithPath("authorName").type(JsonFieldType.STRING)
                            .description("댓글 작성자 닉네임"),
                        fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN)
                            .description("댓글 익명 여부"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("댓글 내용"),
                        fieldWithPath("favoriteCount").type(JsonFieldType.NUMBER)
                            .description("댓글 좋아요 개수"),
                        fieldWithPath("createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("댓글 생성 시간"),
                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("댓글 수정 시간")
                    )
                )
            )
    }

    @Test
    internal fun `destroyByCommentId - 댓글 삭제 API 문서화`() {
        // given
        willDoNothing().given(commentService).destroyByCommentId(anyLong())
        given(userRepository.findAll()).willReturn(listOf(USER_1))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.delete("$COMMENT_API_URI/{commentId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
            )
            .andExpect(status().isNoContent)
            .andDo(
                MockMvcRestDocumentation.document(
                    "comment/delete",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(
                        parameterWithName("commentId").description("삭제할 댓글 ID")
                    ),
                    HeaderDocumentation.requestHeaders(
                        HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(로그인시 발급되는) 인증 토큰")
                    )
                )
            )
    }
}
