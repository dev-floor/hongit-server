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
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.ARTICLE_ID_1
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
import org.mockito.BDDMockito.*
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
    fun `showByArticleId - 게시글 상세조회 문서화`() {
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
                        parameterWithName("id").description("상세조회 할 게시글 ID")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER)
                            .description("게시글 ID"),
                        fieldWithPath("options[].id").type(JsonFieldType.NUMBER)
                            .description("게시글의 옵션 ID"),
                        fieldWithPath("options[].text").type(JsonFieldType.STRING)
                            .description("게시글의 옵션 설명"),
                        fieldWithPath("options[].type.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("게시글의 옵션 타입"),
                        fieldWithPath("options[].type.text").type(JsonFieldType.STRING)
                            .optional()
                            .description("게시글의 옵션 타입 설명"),
                        fieldWithPath("title").type(JsonFieldType.STRING)
                            .description("게시글 제목"),
                        fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN)
                            .description("게시글 익명 여부"),
                        fieldWithPath("author.nickname").type(JsonFieldType.STRING)
                            .description("게시글의 작성자 닉네임"),
                        fieldWithPath("author.type.id").type(JsonFieldType.STRING)
                            .enumFormat(UserType::class)
                            .description("게시글의 작성자 유형"),
                        fieldWithPath("author.type.text").type(JsonFieldType.STRING)
                            .description("게시글의 작성자 유형 설명"),
                        fieldWithPath("author.image").type(JsonFieldType.STRING)
                            .optional()
                            .description("게시글의 작성자 프로필 사진 URL"),
                        fieldWithPath("author.github").type(JsonFieldType.STRING)
                            .optional()
                            .description("게시글의 작성자 Github 계정"),
                        fieldWithPath("author.blog").type(JsonFieldType.STRING)
                            .optional()
                            .description("게시글의 작성자 블로그 주소"),
                        fieldWithPath("author.description").type(JsonFieldType.STRING)
                            .optional()
                            .description("게시글의 작성자 소개"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("게시글 내용"),
                        fieldWithPath("hashtags[]").type(JsonFieldType.ARRAY)
                            .optional()
                            .description("게시글 해시태그"),
                        fieldWithPath("favoriteCount").type(JsonFieldType.NUMBER)
                            .description("게시글 좋아요 갯수"),
                        fieldWithPath("wonderCount").type(JsonFieldType.NUMBER)
                            .description("게시글 궁금해요 갯수"),
                        fieldWithPath("clipCount").type(JsonFieldType.NUMBER)
                            .description("게시글 스크랩 갯수"),
                        fieldWithPath("createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("게시글 생성 시간"),
                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("게시글 수정 시간")
                    )
                )
            )
    }

    @Test
    fun `showAllByBoardId - 게시글 목록조회(게시판) 문서화`() {
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
                        parameterWithName("boardId").description("게시글 목록을 조회할 게시판 ID"),
                        parameterWithName("sort").description("정렬 기준"),
                        parameterWithName("options").description("게시판 옵션 내용"),
                        parameterWithName("page").description("게시글 목록 페이지 번호"),
                        parameterWithName("pageSize").description("페이지 크기")

                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .description("게시글 ID"),
                        fieldWithPath("[].options[].id").type(JsonFieldType.NUMBER)
                            .description("게시글의 옵션 ID"),
                        fieldWithPath("[].options[].text").type(JsonFieldType.STRING)
                            .description("게시글의 옵션 설명"),
                        fieldWithPath("[].options[].type.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("게시글의 옵션 타입"),
                        fieldWithPath("[].options[].type.text").type(JsonFieldType.STRING)
                            .optional()
                            .description("게시글의 옵션 타입 설명"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING)
                            .description("게시글 제목"),
                        fieldWithPath("[].anonymous").type(JsonFieldType.BOOLEAN)
                            .description("게시글 익명 여부"),
                        fieldWithPath("[].authorName").type(JsonFieldType.STRING)
                            .description("게시글의 작성자 닉네임"),
                        fieldWithPath("[].content").type(JsonFieldType.STRING)
                            .description("게시글 내용"),
                        fieldWithPath("[].favoriteCount").type(JsonFieldType.NUMBER)
                            .description("게시글 좋아요 갯수"),
                        fieldWithPath("[].wonderCount").type(JsonFieldType.NUMBER)
                            .description("게시글 궁금해요 갯수"),
                        fieldWithPath("[].clipCount").type(JsonFieldType.NUMBER)
                            .description("게시글 스크랩 갯수"),
                        fieldWithPath("[].page").type(JsonFieldType.NUMBER)
                            .description("페이지 번호"),
                        fieldWithPath("[].totalArticleCount").type(JsonFieldType.NUMBER)
                            .description("전체 게시글 개수"),

                        fieldWithPath("[].createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("게시글 생성 시간"),
                        fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("게시글 수정 시간")
                    )
                )
            )
    }

    @Test
    fun `showAllByNickname- 게시글 목록조회(사용자) 문서화`() {
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
                            .description("(로그인시 발급되는) 인증 토큰")
                    ),
                    RequestDocumentation.requestParameters(
                        parameterWithName("nickname").description("게시글 목록을 조회할 작성자 닉네임"),
                        parameterWithName("page").description("게시글 목록 페이지 번호"),
                        parameterWithName("pageSize").description("페이지 크기")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .description("게시글 ID"),
                        fieldWithPath("[].options[].id").type(JsonFieldType.NUMBER)
                            .description("게시글의 옵션 ID"),
                        fieldWithPath("[].options[].text").type(JsonFieldType.STRING)
                            .description("게시글의 옵션 설명"),
                        fieldWithPath("[].options[].type.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("게시글의 옵션 타입"),
                        fieldWithPath("[].options[].type.text").type(JsonFieldType.STRING)
                            .optional()
                            .description("게시글의 옵션 타입 설명"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING)
                            .description("게시글 제목"),
                        fieldWithPath("[].anonymous").type(JsonFieldType.BOOLEAN)
                            .description("게시글 익명 여부"),
                        fieldWithPath("[].authorName").type(JsonFieldType.STRING)
                            .description("게시글의 작성자 닉네임"),
                        fieldWithPath("[].content").type(JsonFieldType.STRING)
                            .description("게시글 내용"),
                        fieldWithPath("[].favoriteCount").type(JsonFieldType.NUMBER)
                            .description("게시글 좋아요 갯수"),
                        fieldWithPath("[].wonderCount").type(JsonFieldType.NUMBER)
                            .description("게시글 궁금해요 갯수"),
                        fieldWithPath("[].clipCount").type(JsonFieldType.NUMBER)
                            .description("게시글 스크랩 갯수"),
                        fieldWithPath("[].page").type(JsonFieldType.NUMBER)
                            .description("페이지 번호"),
                        fieldWithPath("[].totalArticleCount").type(JsonFieldType.NUMBER)
                            .description("전체 게시글 개수"),
                        fieldWithPath("[].createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("게시글 생성 시간"),
                        fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("게시글 수정 시간")
                    )
                )
            )
    }

    @Test
    fun `showAllByFavoritedUserId - 게시글 목록조회(유저가 좋아요한 게시글) 문서화`() {
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
                            .description("(로그인시 발급되는) 인증 토큰")
                    ),
                    RequestDocumentation.requestParameters(
                        parameterWithName("favoritedUserId").description("사용자 아이디"),
                        parameterWithName("favoriteType").description("좋아요 종류"),
                        parameterWithName("page").description("게시글 목록 페이지 번호"),
                        parameterWithName("pageSize").description("페이지 크기")
                    ),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .description("게시글 ID"),
                        fieldWithPath("[].options[].id").type(JsonFieldType.NUMBER)
                            .description("게시글의 옵션 ID"),
                        fieldWithPath("[].options[].text").type(JsonFieldType.STRING)
                            .description("게시글의 옵션 설명"),
                        fieldWithPath("[].options[].type.id").type(JsonFieldType.STRING)
                            .optional()
                            .enumFormat(OptionType::class)
                            .description("게시글의 옵션 타입"),
                        fieldWithPath("[].options[].type.text").type(JsonFieldType.STRING)
                            .optional()
                            .description("게시글의 옵션 타입 설명"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING)
                            .description("게시글 제목"),
                        fieldWithPath("[].anonymous").type(JsonFieldType.BOOLEAN)
                            .description("게시글 익명 여부"),
                        fieldWithPath("[].authorName").type(JsonFieldType.STRING)
                            .description("게시글의 작성자 닉네임"),
                        fieldWithPath("[].content").type(JsonFieldType.STRING)
                            .description("게시글 내용"),
                        fieldWithPath("[].favoriteCount").type(JsonFieldType.NUMBER)
                            .description("게시글 좋아요 갯수"),
                        fieldWithPath("[].wonderCount").type(JsonFieldType.NUMBER)
                            .description("게시글 궁금해요 갯수"),
                        fieldWithPath("[].clipCount").type(JsonFieldType.NUMBER)
                            .description("게시글 스크랩 갯수"),
                        fieldWithPath("[].page").type(JsonFieldType.NUMBER)
                            .description("페이지 번호"),
                        fieldWithPath("[].totalArticleCount").type(JsonFieldType.NUMBER)
                            .description("전체 게시글 개수"),

                        fieldWithPath("[].createdAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("게시글 생성 시간"),
                        fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING)
                            .dateTimeFormat()
                            .description("게시글 수정 시간")
                    )
                )
            )
    }

    @Test
    fun `create - 게시글 생성 API 문서화`() {
        // given
        given(articleService.create(any(), any())).willReturn(ARTICLE_ID_1)
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
                            .description("(로그인시 발급되는) 인증 토큰")
                    ),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("optionIds").type(JsonFieldType.ARRAY)
                            .description("게시글 해당 옵션 리스트"),
                        fieldWithPath("title").type(JsonFieldType.STRING)
                            .description("게시글 제목"),
                        fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN)
                            .description("게시글 익명 여부"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("게시글 내용"),
                        fieldWithPath("hashtagNames").type(JsonFieldType.ARRAY)
                            .description("게시글 해시태그 이름들"),
                        fieldWithPath("boardId").type(JsonFieldType.NUMBER)
                            .description("게시글이 포함되는 게시판 ID"),
                    ),
                    HeaderDocumentation.responseHeaders(
                        headerWithName("Location").description("생성된 게시글의 ID가 담긴 URI")
                    )
                )
            )
    }

    @Test
    fun `modifyByArticleId - 게시글 수정 API 문서화`() {
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
                        parameterWithName("articleId").description("수정할 게시글 ID")
                    ),
                    HeaderDocumentation.requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(로그인시 발급되는) 인증 토큰")
                    ),
                    PayloadDocumentation.requestFields(
                        fieldWithPath("optionIds").type(JsonFieldType.ARRAY)
                            .description("수정 option Id 리스트"),
                        fieldWithPath("title").type(JsonFieldType.STRING)
                            .description("수정 게시글 제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING)
                            .description("수정 게시글 내용"),
                        fieldWithPath("hashtagNames").type(JsonFieldType.ARRAY)
                            .description("수정 해시태그 제목 리스트")
                    ),
                )
            )
    }

    @Test
    fun `destroyArticleId - 게시글 삭제 API 문서화`() {
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
                        parameterWithName("articleId").description("삭제할 게시글 ID")
                    ),
                    HeaderDocumentation.requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).authorizationFormat()
                            .description("(로그인시 발급되는) 인증 토큰")
                    )
                )
            )
    }
}
