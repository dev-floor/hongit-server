package com.devfloor.hongit.api.docs

import com.devfloor.hongit.api.article.application.ArticleService
import com.devfloor.hongit.api.article.presentation.ArticleController
import com.devfloor.hongit.api.article.presentation.ArticleController.Companion.ARTICLE_API_URI
import com.devfloor.hongit.api.support.ApiDocsTest
import com.devfloor.hongit.api.support.ApiDocsTestUtils
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.authorizationFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.dateTimeFormat
import com.devfloor.hongit.api.support.ApiDocumentFormatGenerator.enumFormat
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.ARTICLE_CREATE_REQUEST_1
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.ARTICLE_FEED_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.ARTICLE_MODIFY_REQUEST_1
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.ARTICLE_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.ArticleSortTypeFixture.ARTICLE_SORT_TYPE_1
import com.devfloor.hongit.api.support.TestFixtures.FavoriteTypeFixture.FAVORITE_TYPE_1
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.USER_1
import com.devfloor.hongit.core.option.domain.OptionType
import com.devfloor.hongit.core.user.domain.UserRepository
import com.devfloor.hongit.core.user.domain.UserType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willDoNothing
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
import org.springframework.util.LinkedMultiValueMap

@ApiDocsTest
internal class ArticleControllerDocs {
    @InjectMocks
    private lateinit var articleController: ArticleController

    @Mock
    private lateinit var articleService: ArticleService

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = ApiDocsTestUtils.getRestDocsMockMvcWithLoginUser(restDocumentation, articleController, userRepository)
    }

    @Test
    fun `showByArticleId - ????????? ???????????? ?????????`() {
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
                        parameterWithName("id").description("???????????? ??? ????????? ID")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER)
                            .description("????????? ID"),
                        fieldWithPath("options[].id").type(JsonFieldType.NUMBER)
                            .description("???????????? ?????? ID"),
                        fieldWithPath("options[].text").type(JsonFieldType.STRING)
                            .description("???????????? ?????? ??????"),
                        fieldWithPath("options[].type.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("???????????? ?????? ??????"),
                        fieldWithPath("options[].type.text").type(JsonFieldType.STRING)
                            .optional()
                            .description("???????????? ?????? ?????? ??????"),
                        fieldWithPath("title").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN)
                            .description("????????? ?????? ??????"),
                        fieldWithPath("author.nickname").type(JsonFieldType.STRING)
                            .description("???????????? ????????? ?????????"),
                        fieldWithPath("author.type.id").type(JsonFieldType.STRING)
                            .enumFormat(UserType::class)
                            .description("???????????? ????????? ??????"),
                        fieldWithPath("author.type.text").type(JsonFieldType.STRING)
                            .description("???????????? ????????? ?????? ??????"),
                        fieldWithPath("author.image").type(JsonFieldType.STRING)
                            .optional()
                            .description("???????????? ????????? ????????? ?????? URL"),
                        fieldWithPath("author.github").type(JsonFieldType.STRING)
                            .optional()
                            .description("???????????? ????????? Github ??????"),
                        fieldWithPath("author.blog").type(JsonFieldType.STRING)
                            .optional()
                            .description("???????????? ????????? ????????? ??????"),
                        fieldWithPath("author.description").type(JsonFieldType.STRING)
                            .optional()
                            .description("???????????? ????????? ??????"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("hashtags[]").type(JsonFieldType.ARRAY)
                            .optional()
                            .description("????????? ????????????"),
                        fieldWithPath("favoriteCount").type(JsonFieldType.NUMBER)
                            .description("????????? ????????? ??????"),
                        fieldWithPath("wonderCount").type(JsonFieldType.NUMBER)
                            .description("????????? ???????????? ??????"),
                        fieldWithPath("clipCount").type(JsonFieldType.NUMBER)
                            .description("????????? ????????? ??????"),
                        fieldWithPath("createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("????????? ?????? ??????"),
                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("????????? ?????? ??????")
                    )
                )
            )
    }

    @Test
    fun `showAllByBoardId - ????????? ????????????(?????????) ?????????`() {
        // given
        given(
            articleService.showAllByBoardId(
                anyLong(),
                anyInt(),
                anyInt(),
                any(),
                anyList()
            )
        ).willReturn(listOf(ARTICLE_FEED_RESPONSE_1))

        val params = LinkedMultiValueMap<String, String>()
        params.add("boardId", "1")
        params.add("sort", ARTICLE_SORT_TYPE_1.toString())
        params.add("options", "1, 2, 3")
        params.add("page", "1")
        params.add("pageSize", "10")
        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get(ARTICLE_API_URI)
                    .params(params)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "article/getAllByBoardId",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.requestParameters(
                        parameterWithName("boardId").description("????????? ????????? ????????? ????????? ID"),
                        parameterWithName("sort").description("?????? ??????"),
                        parameterWithName("options").description("????????? ?????? ??????"),
                        parameterWithName("page").description("????????? ?????? ????????? ??????"),
                        parameterWithName("pageSize").description("????????? ??????")

                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .description("????????? ID"),
                        fieldWithPath("[].options[].id").type(JsonFieldType.NUMBER)
                            .description("???????????? ?????? ID"),
                        fieldWithPath("[].options[].text").type(JsonFieldType.STRING)
                            .description("???????????? ?????? ??????"),
                        fieldWithPath("[].options[].type.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("???????????? ?????? ??????"),
                        fieldWithPath("[].options[].type.text").type(JsonFieldType.STRING)
                            .optional()
                            .description("???????????? ?????? ?????? ??????"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("[].anonymous").type(JsonFieldType.BOOLEAN)
                            .description("????????? ?????? ??????"),
                        fieldWithPath("[].authorName").type(JsonFieldType.STRING)
                            .description("???????????? ????????? ?????????"),
                        fieldWithPath("[].content").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("[].favoriteCount").type(JsonFieldType.NUMBER)
                            .description("????????? ????????? ??????"),
                        fieldWithPath("[].wonderCount").type(JsonFieldType.NUMBER)
                            .description("????????? ???????????? ??????"),
                        fieldWithPath("[].clipCount").type(JsonFieldType.NUMBER)
                            .description("????????? ????????? ??????"),
                        fieldWithPath("[].page").type(JsonFieldType.NUMBER)
                            .description("????????? ??????"),
                        fieldWithPath("[].totalArticleCount").type(JsonFieldType.NUMBER)
                            .description("?????? ????????? ??????"),

                        fieldWithPath("[].createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("????????? ?????? ??????"),
                        fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("????????? ?????? ??????")
                    )
                )
            )
    }

    @Test
    fun `showAllByNickname- ????????? ????????????(?????????) ?????????`() {
        // given
        given(
            articleService.showAllByNickname(
                anyString(),
                anyInt(),
                anyInt(),
                any()
            )
        ).willReturn(listOf(ARTICLE_FEED_RESPONSE_1))

        given(userRepository.findAll()).willReturn(listOf(USER_1))

        val params = LinkedMultiValueMap<String, String>()
        params.add("nickname", "usernicknametest")
        params.add("page", "1")
        params.add("pageSize", "10")

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get(ARTICLE_API_URI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .params(params)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "article/getAllByNickname",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    HeaderDocumentation.requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(???????????? ????????????) ?????? ??????")
                    ),
                    RequestDocumentation.requestParameters(
                        parameterWithName("nickname").description("????????? ????????? ????????? ????????? ?????????"),
                        parameterWithName("page").description("????????? ?????? ????????? ??????"),
                        parameterWithName("pageSize").description("????????? ??????")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .description("????????? ID"),
                        fieldWithPath("[].options[].id").type(JsonFieldType.NUMBER)
                            .description("???????????? ?????? ID"),
                        fieldWithPath("[].options[].text").type(JsonFieldType.STRING)
                            .description("???????????? ?????? ??????"),
                        fieldWithPath("[].options[].type.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("???????????? ?????? ??????"),
                        fieldWithPath("[].options[].type.text").type(JsonFieldType.STRING)
                            .optional()
                            .description("???????????? ?????? ?????? ??????"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("[].anonymous").type(JsonFieldType.BOOLEAN)
                            .description("????????? ?????? ??????"),
                        fieldWithPath("[].authorName").type(JsonFieldType.STRING)
                            .description("???????????? ????????? ?????????"),
                        fieldWithPath("[].content").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("[].favoriteCount").type(JsonFieldType.NUMBER)
                            .description("????????? ????????? ??????"),
                        fieldWithPath("[].wonderCount").type(JsonFieldType.NUMBER)
                            .description("????????? ???????????? ??????"),
                        fieldWithPath("[].clipCount").type(JsonFieldType.NUMBER)
                            .description("????????? ????????? ??????"),
                        fieldWithPath("[].page").type(JsonFieldType.NUMBER)
                            .description("????????? ??????"),
                        fieldWithPath("[].totalArticleCount").type(JsonFieldType.NUMBER)
                            .description("?????? ????????? ??????"),
                        fieldWithPath("[].createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("????????? ?????? ??????"),
                        fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("????????? ?????? ??????")
                    )
                )
            )
    }

    @Test
    fun `showAllByFavoritedUserId - ????????? ????????????(????????? ???????????? ?????????) ?????????`() {
        // given
        given(
            articleService.showAllByFavoritedUserId(
                anyLong(),
                anyInt(),
                anyInt(),
            )
        ).willReturn(listOf(ARTICLE_FEED_RESPONSE_1))

        given(userRepository.findAll()).willReturn(listOf(USER_1))

        val params = LinkedMultiValueMap<String, String>()
        params.add("favoritedUserId", "1")
        params.add("favoriteType", FAVORITE_TYPE_1.toString())
        params.add("page", "1")
        params.add("pageSize", "10")
        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.get(ARTICLE_API_URI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .params(params)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "article/getAllByFavoritedUserId",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    HeaderDocumentation.requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(???????????? ????????????) ?????? ??????")
                    ),
                    RequestDocumentation.requestParameters(
                        parameterWithName("favoritedUserId").description("????????? ?????????"),
                        parameterWithName("favoriteType").description("????????? ??????"),
                        parameterWithName("page").description("????????? ?????? ????????? ??????"),
                        parameterWithName("pageSize").description("????????? ??????")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .description("????????? ID"),
                        fieldWithPath("[].options[].id").type(JsonFieldType.NUMBER)
                            .description("???????????? ?????? ID"),
                        fieldWithPath("[].options[].text").type(JsonFieldType.STRING)
                            .description("???????????? ?????? ??????"),
                        fieldWithPath("[].options[].type.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("???????????? ?????? ??????"),
                        fieldWithPath("[].options[].type.text").type(JsonFieldType.STRING)
                            .optional()
                            .description("???????????? ?????? ?????? ??????"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("[].anonymous").type(JsonFieldType.BOOLEAN)
                            .description("????????? ?????? ??????"),
                        fieldWithPath("[].authorName").type(JsonFieldType.STRING)
                            .description("???????????? ????????? ?????????"),
                        fieldWithPath("[].content").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("[].favoriteCount").type(JsonFieldType.NUMBER)
                            .description("????????? ????????? ??????"),
                        fieldWithPath("[].wonderCount").type(JsonFieldType.NUMBER)
                            .description("????????? ???????????? ??????"),
                        fieldWithPath("[].clipCount").type(JsonFieldType.NUMBER)
                            .description("????????? ????????? ??????"),
                        fieldWithPath("[].page").type(JsonFieldType.NUMBER)
                            .description("????????? ??????"),
                        fieldWithPath("[].totalArticleCount").type(JsonFieldType.NUMBER)
                            .description("?????? ????????? ??????"),

                        fieldWithPath("[].createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("????????? ?????? ??????"),
                        fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("????????? ?????? ??????")
                    )
                )
            )
    }

    @Test
    fun `create - ????????? ?????? API ?????????`() {
        // given
        given(articleService.create(any(), any())).willReturn(ARTICLE_RESPONSE_1.id)
        given(userRepository.findAll()).willReturn(listOf(USER_1))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.post(ARTICLE_API_URI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ApiDocsTestUtils.convertAsJson(ARTICLE_CREATE_REQUEST_1))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated)
            .andDo(
                MockMvcRestDocumentation.document(
                    "article/post",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    HeaderDocumentation.requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(???????????? ????????????) ?????? ??????")
                    ),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("optionIds").type(JsonFieldType.ARRAY)
                            .description("????????? ?????? ?????? ?????????"),
                        fieldWithPath("title").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN)
                            .description("????????? ?????? ??????"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("????????? ??????"),
                        fieldWithPath("hashtagNames").type(JsonFieldType.ARRAY)
                            .description("????????? ???????????? ?????????"),
                        fieldWithPath("boardId").type(JsonFieldType.NUMBER)
                            .description("???????????? ???????????? ????????? ID"),
                    ),
                    HeaderDocumentation.responseHeaders(
                        headerWithName("Location").description("????????? ???????????? ID??? ?????? URI")
                    )
                )
            )
    }

    @Test
    fun `modifyByArticleId - ????????? ?????? API ?????????`() {
        // given
        willDoNothing().given(articleService).modifyByArticleId(anyLong(), any())
        given(userRepository.findAll()).willReturn(listOf(USER_1))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.put("$ARTICLE_API_URI/{articleId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ApiDocsTestUtils.convertAsJson(ARTICLE_MODIFY_REQUEST_1))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent)
            .andDo(
                MockMvcRestDocumentation.document(
                    "article/putByArticleId",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(
                        parameterWithName("articleId").description("????????? ????????? ID")
                    ),
                    HeaderDocumentation.requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(???????????? ????????????) ?????? ??????")
                    ),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("optionIds").type(JsonFieldType.ARRAY)
                            .description("?????? option Id ?????????"),
                        fieldWithPath("title").type(JsonFieldType.STRING)
                            .description("?????? ????????? ??????"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("?????? ????????? ??????"),
                        fieldWithPath("hashtagNames").type(JsonFieldType.ARRAY)
                            .description("?????? ???????????? ?????? ?????????")
                    ),
                )
            )
    }

    @Test
    fun `destroyArticleId - ????????? ?????? API ?????????`() {
        // given
        willDoNothing().given(articleService).destroyByArticleId(anyLong())
        given(userRepository.findAll()).willReturn(listOf(USER_1))

        // when - then
        mockMvc
            .perform(
                RestDocumentationRequestBuilders.delete("$ARTICLE_API_URI/{articleId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer secretsecretsecret")
            )
            .andExpect(status().isNoContent)
            .andDo(
                MockMvcRestDocumentation.document(
                    "article/deleteByArticleId",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(
                        parameterWithName("articleId").description("????????? ????????? ID")
                    ),
                    HeaderDocumentation.requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(???????????? ????????????) ?????? ??????")
                    )
                )
            )
    }
}
