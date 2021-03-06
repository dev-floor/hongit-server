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
    internal fun `showAllByArticleId - ?????? ????????? ?????? ?????? ?????? API ?????????`() {
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
                        parameterWithName("articleId").description("?????? ???????????? ID")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .description("?????? ID"),
                        fieldWithPath("[].authorName").type(JsonFieldType.STRING)
                            .description("?????? ????????? ?????????"),
                        fieldWithPath("[].anonymous").type(JsonFieldType.BOOLEAN)
                            .description("?????? ?????? ??????"),
                        fieldWithPath("[].content").type(JsonFieldType.STRING)
                            .description("?????? ??????"),
                        fieldWithPath("[].favoriteCount").type(JsonFieldType.NUMBER)
                            .description("?????? ????????? ??????"),
                        fieldWithPath("[].createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("?????? ?????? ??????"),
                        fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("?????? ?????? ??????")
                    )
                )
            )
    }

    @Test
    internal fun `showAllByNickname - ?????? ?????? ?????? ?????? ?????? API ?????????`() {
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
                        parameterWithName("nickname").description("?????? ????????? ?????????")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].comment.id").type(JsonFieldType.NUMBER)
                            .description("?????? ID"),
                        fieldWithPath("[].comment.authorName").type(JsonFieldType.STRING)
                            .description("?????? ????????? ?????????"),
                        fieldWithPath("[].comment.anonymous").type(JsonFieldType.BOOLEAN)
                            .description("?????? ?????? ??????"),
                        fieldWithPath("[].comment.content").type(JsonFieldType.STRING)
                            .description("?????? ??????"),
                        fieldWithPath("[].comment.favoriteCount").type(JsonFieldType.NUMBER)
                            .description("?????? ????????? ??????"),
                        fieldWithPath("[].comment.createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("?????? ?????? ??????"),
                        fieldWithPath("[].comment.modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("?????? ?????? ??????"),
                        fieldWithPath("[].articleId").type(JsonFieldType.NUMBER)
                            .description("?????? ?????? ????????? ID"),
                        fieldWithPath("[].articleTitle").type(JsonFieldType.STRING)
                            .optional()
                            .description("?????? ?????? ????????? ??????"),
                    )
                )
            )
    }

    @Test
    internal fun `create - ?????? ?????? API ?????????`() {
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
                            .description("(???????????? ????????????) ?????? ??????")
                    ),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("articleId").type(JsonFieldType.NUMBER)
                            .description("?????? ?????? ????????? ID"),
                        fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN)
                            .description("?????? ?????? ??????"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("?????? ??????"),
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER)
                            .description("?????? ID"),
                        fieldWithPath("authorName").type(JsonFieldType.STRING)
                            .description("?????? ????????? ?????????"),
                        fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN)
                            .description("?????? ?????? ??????"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("?????? ??????"),
                        fieldWithPath("favoriteCount").type(JsonFieldType.NUMBER)
                            .description("?????? ????????? ??????"),
                        fieldWithPath("createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("?????? ?????? ??????"),
                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("?????? ?????? ??????")
                    )
                )
            )
    }

    @Test
    internal fun `modifyByCommentId - ?????? ?????? API ?????????`() {
        // given
        given(commentService.modifyByCommentId(anyLong(), any())).willReturn(COMMENT_RESPONSE_1)
        given(userRepository.findAll()).willReturn(listOf(USER_1))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.put("$COMMENT_API_URI/{commentId}", COMMENT_RESPONSE_1.id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ApiDocsTestUtils.convertAsJson(COMMENT_MODIFY_REQUEST_1))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "comment/putByCommentId",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(
                        parameterWithName("commentId").description("????????? ?????? ID")
                    ),
                    HeaderDocumentation.requestHeaders(
                        HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(???????????? ????????????) ?????? ??????")
                    ),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("?????? ?????? ??????"),
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER)
                            .description("?????? ID"),
                        fieldWithPath("authorName").type(JsonFieldType.STRING)
                            .description("?????? ????????? ?????????"),
                        fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN)
                            .description("?????? ?????? ??????"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("?????? ??????"),
                        fieldWithPath("favoriteCount").type(JsonFieldType.NUMBER)
                            .description("?????? ????????? ??????"),
                        fieldWithPath("createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("?????? ?????? ??????"),
                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("?????? ?????? ??????")
                    )
                )
            )
    }

    @Test
    internal fun `destroyByCommentId - ?????? ?????? API ?????????`() {
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
                    "comment/deleteByCommentId",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(
                        parameterWithName("commentId").description("????????? ?????? ID")
                    ),
                    HeaderDocumentation.requestHeaders(
                        HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(???????????? ????????????) ?????? ??????")
                    )
                )
            )
    }
}
