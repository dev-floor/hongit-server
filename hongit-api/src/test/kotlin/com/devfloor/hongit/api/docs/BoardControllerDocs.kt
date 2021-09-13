package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.board.application.BoardService
import com.devfloor.hongit.api.board.presentation.BoardController
import com.devfloor.hongit.api.board.presentation.BoardController.Companion.BOARD_API_URI
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.authorizationFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.enumFormat
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.api.support.TestFixtures.BoardFixture.BOARD_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.BoardFixture.BOARD_SIMPLE_RESPONSE_1
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.course.domain.Grade
import com.devfloor.hongit.core.option.domain.OptionType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyLong
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
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
internal class BoardControllerDocs {
    @InjectMocks
    private lateinit var boardController: BoardController

    @Mock
    private lateinit var boardService: BoardService

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvc(restDocumentation, boardController)
    }

    @Test
    internal fun `showByBoardId - 게시판 상세조회 문서화`() {
        // given
        given(boardService.showByBoardId(anyLong())).willReturn(BOARD_RESPONSE_1)

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get("$BOARD_API_URI/{id}", BOARD_RESPONSE_1.id)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "board/getByBoardId",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(
                        parameterWithName("id").description("상세조회 할 게시판 ID")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER)
                            .description("게시판 ID"),
                        fieldWithPath("title").type(JsonFieldType.STRING)
                            .description("게시판 제목"),
                        fieldWithPath("professor.name").type(JsonFieldType.STRING)
                            .optional()
                            .description("(수업게시판만) 게시판 수업의 교수 이름"),
                        fieldWithPath("professor.email").type(JsonFieldType.STRING)
                            .optional()
                            .description("(수업게시판만) 게시판 수업의 교수 이메일"),
                        fieldWithPath("subject").type(JsonFieldType.STRING)
                            .optional()
                            .description("(수업게시판만) 게시판 수업의 과목"),
                        fieldWithPath("type.id").type(JsonFieldType.STRING)
                            .enumFormat(BoardType::class)
                            .description("게시판 유형"),
                        fieldWithPath("type.text").type(JsonFieldType.STRING)
                            .enumFormat(BoardType::class)
                            .description("게시판 유형 설명"),
                        fieldWithPath("grade.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(Grade::class)
                            .description("(수업게시판만) 게시판 수업의 개설 학년"),
                        fieldWithPath("grade.text").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(Grade::class)
                            .description("(수업게시판만) 게시판 수업의 개설 학년 설명"),
                        fieldWithPath("options[].id").type(JsonFieldType.NUMBER)
                            .optional()
                            .description("게시판 옵션의 ID"),
                        fieldWithPath("options[].text").type(JsonFieldType.STRING)
                            .optional()
                            .description("게시판 옵션의 설명"),
                        fieldWithPath("options[].type.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("게시판 옵션의 유형"),
                        fieldWithPath("options[].type.text").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("게시판 옵션의 유형 설명"),
                    )
                )
            )
    }

    @Test
    internal fun `showAll - 게시판 목록 조회 문서화`() {
        // given
        given(boardService.showAll()).willReturn(listOf(BOARD_SIMPLE_RESPONSE_1))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get(BOARD_API_URI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "board/getAll",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    HeaderDocumentation.requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(로그인시 발급되는) 인증 토큰")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .description("게시판 ID"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING)
                            .description("게시판 제목"),
                        fieldWithPath("[].type.id").type(JsonFieldType.STRING)
                            .enumFormat(BoardType::class)
                            .description("게시판 유형"),
                        fieldWithPath("[].type.text").type(JsonFieldType.STRING)
                            .enumFormat(BoardType::class)
                            .description("게시판 유형 설명"),
                        fieldWithPath("[].grade.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(Grade::class)
                            .description("(수업게시판만) 게시판 수업의 개설 학년"),
                        fieldWithPath("[].grade.text").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(Grade::class)
                            .description("(수업게시판만) 게시판 수업의 개설 학년 설명"),
                    )
                )
            )
    }

    @Test
    internal fun `showAllBoardByBoardType - 게시판 유형별 조회 문서화`() {
        // given
        given(boardService.showAllBoardByBoardType(any())).willReturn(listOf(BOARD_RESPONSE_1))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get(BOARD_API_URI)
                    .param("type", BoardType.COURSE_BOARD.id)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "board/getAllByBoardType",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.requestParameters(
                        parameterWithName("type").enumFormat(BoardType::class)
                            .description("조회할 게시판 유형")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .description("게시판 ID"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING)
                            .description("게시판 제목"),
                        fieldWithPath("[].professor.name").type(JsonFieldType.STRING)
                            .optional()
                            .description("(수업게시판만) 게시판 수업의 교수 이름"),
                        fieldWithPath("[].professor.email").type(JsonFieldType.STRING)
                            .optional()
                            .description("(수업게시판만) 게시판 수업의 교수 이메일"),
                        fieldWithPath("[].subject").type(JsonFieldType.STRING)
                            .optional()
                            .description("(수업게시판만) 게시판 수업의 과목"),
                        fieldWithPath("[].type.id").type(JsonFieldType.STRING)
                            .enumFormat(BoardType::class)
                            .description("게시판 유형"),
                        fieldWithPath("[].type.text").type(JsonFieldType.STRING)
                            .enumFormat(BoardType::class)
                            .description("게시판 유형 설명"),
                        fieldWithPath("[].grade.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(Grade::class)
                            .description("(수업게시판만) 게시판 수업의 개설 학년"),
                        fieldWithPath("[].grade.text").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(Grade::class)
                            .description("(수업게시판만) 게시판 수업의 개설 학년 설명"),
                        fieldWithPath("[].options[].id").type(JsonFieldType.NUMBER)
                            .optional()
                            .description("게시판 옵션의 ID"),
                        fieldWithPath("[].options[].text").type(JsonFieldType.STRING)
                            .optional()
                            .description("게시판 옵션의 설명"),
                        fieldWithPath("[].options[].type.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("게시판 옵션의 유형"),
                        fieldWithPath("[].options[].type.text").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("게시판 옵션의 유형 설명"),
                    )
                )
            )
    }
}
