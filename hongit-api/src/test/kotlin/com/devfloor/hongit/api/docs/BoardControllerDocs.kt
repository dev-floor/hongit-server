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
    internal fun `showByBoardId - ????????? ?????? ?????? API ?????????`() {
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
                        parameterWithName("id").description("???????????? ??? ????????? ID")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER)
                            .description("????????? ID"),
                        fieldWithPath("title").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("professor.name").type(JsonFieldType.STRING)
                            .optional()
                            .description("(??????????????????) ????????? ????????? ?????? ??????"),
                        fieldWithPath("professor.email").type(JsonFieldType.STRING)
                            .optional()
                            .description("(??????????????????) ????????? ????????? ?????? ?????????"),
                        fieldWithPath("subject").type(JsonFieldType.STRING)
                            .optional()
                            .description("(??????????????????) ????????? ????????? ??????"),
                        fieldWithPath("type.id").type(JsonFieldType.STRING)
                            .enumFormat(BoardType::class)
                            .description("????????? ??????"),
                        fieldWithPath("type.text").type(JsonFieldType.STRING)
                            .description("????????? ?????? ??????"),
                        fieldWithPath("grade.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(Grade::class)
                            .description("(??????????????????) ????????? ????????? ?????? ??????"),
                        fieldWithPath("grade.text").type(JsonFieldType.STRING)
                            .optional()
                            .description("(??????????????????) ????????? ????????? ?????? ?????? ??????"),
                        fieldWithPath("options[].id").type(JsonFieldType.NUMBER)
                            .optional()
                            .description("????????? ????????? ID"),
                        fieldWithPath("options[].text").type(JsonFieldType.STRING)
                            .optional()
                            .description("????????? ????????? ??????"),
                        fieldWithPath("options[].type.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("????????? ????????? ??????"),
                        fieldWithPath("options[].type.text").type(JsonFieldType.STRING)
                            .optional()
                            .description("????????? ????????? ?????? ??????"),
                    )
                )
            )
    }

    @Test
    internal fun `showAll - ????????? ?????? ?????? API ?????????`() {
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
                            .description("(???????????? ????????????) ?????? ??????")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .description("????????? ID"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("[].type.id").type(JsonFieldType.STRING)
                            .enumFormat(BoardType::class)
                            .description("????????? ??????"),
                        fieldWithPath("[].type.text").type(JsonFieldType.STRING)
                            .description("????????? ?????? ??????"),
                        fieldWithPath("[].grade.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(Grade::class)
                            .description("(??????????????????) ????????? ????????? ?????? ??????"),
                        fieldWithPath("[].grade.text").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(Grade::class)
                            .description("(??????????????????) ????????? ????????? ?????? ?????? ??????"),
                    )
                )
            )
    }

    @Test
    internal fun `showAllBoardByBoardType - ????????? ????????? ?????? API ?????????`() {
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
                            .description("????????? ????????? ??????")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .description("????????? ID"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("[].professor.name").type(JsonFieldType.STRING)
                            .optional()
                            .description("(??????????????????) ????????? ????????? ?????? ??????"),
                        fieldWithPath("[].professor.email").type(JsonFieldType.STRING)
                            .optional()
                            .description("(??????????????????) ????????? ????????? ?????? ?????????"),
                        fieldWithPath("[].subject").type(JsonFieldType.STRING)
                            .optional()
                            .description("(??????????????????) ????????? ????????? ??????"),
                        fieldWithPath("[].type.id").type(JsonFieldType.STRING)
                            .enumFormat(BoardType::class)
                            .description("????????? ??????"),
                        fieldWithPath("[].type.text").type(JsonFieldType.STRING)
                            .description("????????? ?????? ??????"),
                        fieldWithPath("[].grade.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(Grade::class)
                            .description("(??????????????????) ????????? ????????? ?????? ??????"),
                        fieldWithPath("[].grade.text").type(JsonFieldType.STRING)
                            .optional()
                            .description("(??????????????????) ????????? ????????? ?????? ?????? ??????"),
                        fieldWithPath("[].options[].id").type(JsonFieldType.NUMBER)
                            .optional()
                            .description("????????? ????????? ID"),
                        fieldWithPath("[].options[].text").type(JsonFieldType.STRING)
                            .optional()
                            .description("????????? ????????? ??????"),
                        fieldWithPath("[].options[].type.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("????????? ????????? ??????"),
                        fieldWithPath("[].options[].type.text").type(JsonFieldType.STRING)
                            .optional()
                            .description("????????? ????????? ?????? ??????"),
                    )
                )
            )
    }
}
