package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.article.application.ArticleService
import com.devfloor.hongit.api.article.presentation.ArticleController
import com.devfloor.hongit.api.article.presentation.ArticleController.Companion.ARTICLE_API_URI
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.dateTimeFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.emptyFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.enumFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.optional
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.required
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.ARTICLE_RESPONSE_1
import com.devfloor.hongit.core.option.domain.OptionType
import com.devfloor.hongit.core.user.domain.UserType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyLong
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
internal class ArticleControllerDocs {
    @InjectMocks
    private lateinit var articleController: ArticleController

    @Mock
    private lateinit var articleService: ArticleService

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvc(restDocumentation, articleController)
    }

    @Test
    internal fun `showByArticleId - 게시글 상세조회 문서화`() {
        // given
        given(articleService.showByArticleId(anyLong())).willReturn(ARTICLE_RESPONSE_1)

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get("$ARTICLE_API_URI/{id}", ARTICLE_RESPONSE_1.id)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "article/getByArticleId",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(
                        parameterWithName("id").attributes(emptyFormat(), required()).description("상세조회 할 게시글 ID")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER)
                            .attributes(emptyFormat(), required())
                            .description("게시글 ID"),
                        fieldWithPath("options[].id").type(JsonFieldType.NUMBER)
                            .attributes(emptyFormat(), required())
                            .description("게시글의 옵션 ID"),
                        fieldWithPath("options[].text").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), required())
                            .description("게시글의 옵션 설명"),
                        fieldWithPath("options[].type.id").type(JsonFieldType.STRING)
                            .attributes(enumFormat(OptionType::class.java), optional())
                            .description("게시글의 옵션 타입"),
                        fieldWithPath("options[].type.text").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), optional())
                            .description("게시글의 옵션 타입 설명"),
                        fieldWithPath("title").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), required())
                            .description("게시글 제목"),
                        fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN)
                            .attributes(emptyFormat(), required())
                            .description("게시글 익명 여부"),
                        fieldWithPath("author.username").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), required())
                            .description("게시글의 작성자 계정"),
                        fieldWithPath("author.nickname").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), required())
                            .description("게시글의 작성자 닉네임"),
                        fieldWithPath("author.type.id").type(JsonFieldType.STRING)
                            .attributes(enumFormat(UserType::class.java), required())
                            .description("게시글의 작성자 유형"),
                        fieldWithPath("author.type.text").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), required())
                            .description("게시글의 작성자 유형 설명"),
                        fieldWithPath("author.image").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), optional())
                            .description("게시글의 작성자 프로필 사진 URL"),
                        fieldWithPath("author.github").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), optional())
                            .description("게시글의 작성자 Github 계정"),
                        fieldWithPath("author.blog").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), optional())
                            .description("게시글의 작성자 블로그 주소"),
                        fieldWithPath("author.description").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), optional())
                            .description("게시글의 작성자 소개"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .attributes(emptyFormat(), required())
                            .description("게시글 내용"),
                        fieldWithPath("hashtags[]").type(JsonFieldType.ARRAY)
                            .attributes(emptyFormat(), optional())
                            .description("게시글 해시태그"),
                        fieldWithPath("favoriteCount").type(JsonFieldType.NUMBER)
                            .attributes(emptyFormat(), required())
                            .description("게시글 좋아요 갯수"),
                        fieldWithPath("wonderCount").type(JsonFieldType.NUMBER)
                            .attributes(emptyFormat(), required())
                            .description("게시글 궁금해요 갯수"),
                        fieldWithPath("clipCount").type(JsonFieldType.NUMBER)
                            .attributes(emptyFormat(), required())
                            .description("게시글 스크랩 갯수"),
                        fieldWithPath("createdAt").type(JsonFieldType.STRING)
                            .attributes(dateTimeFormat(), required())
                            .description("게시글 생성 시간"),
                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING)
                            .attributes(dateTimeFormat(), required())
                            .description("게시글 수정 시간")
                    )
                )
            )
    }
}