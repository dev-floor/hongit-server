package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.boardbookmark.application.BoardBookmarkModifyRequest
import com.devfloor.hongit.api.boardbookmark.application.BoardBookmarkService
import com.devfloor.hongit.api.boardbookmark.presentation.BoardBookmarkController
import com.devfloor.hongit.api.boardbookmark.presentation.BoardBookmarkController.Companion.BOARD_BOOKMARK_URI
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.authorizationFormat
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.USER_1
import com.devfloor.hongit.core.user.domain.UserRepository
import com.devfloor.hongit.api.support.MockitoHelper.any
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willDoNothing
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ApiDocsTest
internal class BoardBookmarkControllerDocs {
    @InjectMocks
    private lateinit var boardBookmarkController: BoardBookmarkController

    @Mock
    private lateinit var boardBookmarkService: BoardBookmarkService

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvcWithLoginUser(
            restDocumentation,
            boardBookmarkController,
            userRepository,
        )
    }

    @Test
    internal fun `modifyAllByBoardIds - 게시판 즐겨찾기 API 문서화`() {
        willDoNothing().given(boardBookmarkService).modifyAllByBoardIds(any(), any())
        given(userRepository.findAll()).willReturn(listOf(USER_1))
        val request: String = ApiDocsTestUtils.convertAsJson(BoardBookmarkModifyRequest(listOf(0, 1, 2)))

        mockMvc
            .perform(
                put(BOARD_BOOKMARK_URI)
                    .header(AUTHORIZATION, "Bearer secretsecretsecret")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent)
            .andDo(
                MockMvcRestDocumentation.document(
                    "boardBookmark/modify",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(AUTHORIZATION).authorizationFormat()
                            .description("(로그인시 발급되는) 인증 토큰")
                    ),
                    requestFields(
                        fieldWithPath("boardIds[]").type(JsonFieldType.ARRAY)
                            .description("게시판 ID"),
                    ),
                )
            )
    }
}
