package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.home.application.HomeBoardService
import com.devfloor.hongit.api.home.presentation.HomeBoardController
import com.devfloor.hongit.api.home.presentation.HomeBoardController.Companion.HOME_API_URI
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.dateTimeFormat
import com.devfloor.hongit.api.support.TestFixtures.HomeBoardFixture.HOME_BOARD_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.HomeBoardFixture.HOME_BOARD_RESPONSE_2
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ApiDocsTest
internal class HomeBoardControllerTestDocs {
    @InjectMocks
    private lateinit var homeBoardController: HomeBoardController

    @Mock
    private lateinit var homeBoardService: HomeBoardService

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvc(restDocumentation, homeBoardController)
    }

    @Test
    internal fun `showAll - 홈 화면 조회 문서화`() {
        given(homeBoardService.showAll()).willReturn(listOf(HOME_BOARD_RESPONSE_1, HOME_BOARD_RESPONSE_2))

        mockMvc
            .perform(get(HOME_API_URI))
            .andExpect(status().isOk)
            .andDo(
                document(
                    "homeBoard/showAll",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("[].boardId").type(JsonFieldType.NUMBER)
                            .description("게시판 ID"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING)
                            .description("게시판 제목"),
                        fieldWithPath("[].articles[].articleId").type(JsonFieldType.NUMBER)
                            .description("게시글 ID"),
                        fieldWithPath("[].articles[].title").type(JsonFieldType.STRING)
                            .description("게시글 제목"),
                        fieldWithPath("[].articles[].favoriteCount").type(JsonFieldType.NUMBER)
                            .description("게시글 좋아요 갯수"),
                        fieldWithPath("[].articles[].wonderCount").type(JsonFieldType.NUMBER)
                            .description("게시글 궁금해요 갯수"),
                        fieldWithPath("[].articles[].clipCount").type(JsonFieldType.NUMBER)
                            .description("게시글 스크랩 갯수"),
                        fieldWithPath("[].articles[].createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("게시글 생성 시간"),
                        fieldWithPath("[].articles[].modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("게시글 수정 시간")
                    )
                )
            )
    }
}
