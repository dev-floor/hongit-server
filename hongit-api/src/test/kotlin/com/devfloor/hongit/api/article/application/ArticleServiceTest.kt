package com.devfloor.hongit.api.article.application

import com.devfloor.hongit.api.article.application.request.ArticleCreateRequest
import com.devfloor.hongit.api.article.application.request.ArticleModifyRequest
import com.devfloor.hongit.api.article.application.response.ArticleFeedResponse
import com.devfloor.hongit.api.article.application.response.ArticleHomeResponse
import com.devfloor.hongit.api.article.application.response.ArticleResponse
import com.devfloor.hongit.api.article.domain.ArticleRepositoryCustom
import com.devfloor.hongit.api.article.domain.ArticleSortType
import com.devfloor.hongit.api.articlehashtag.application.ArticleHashtagService
import com.devfloor.hongit.api.articleoption.application.ArticleOptionService
import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.hashtag.application.HashtagService
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.article.domain.ArticleRepository
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavorite
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteRepository
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.hongit.core.articlehashtag.domain.ArticleHashtag
import com.devfloor.hongit.core.articlehashtag.domain.ArticleHashtagRepository
import com.devfloor.hongit.core.articleoption.domain.ArticleOption
import com.devfloor.hongit.core.articleoption.domain.ArticleOptionRepository
import com.devfloor.hongit.core.articleviewcount.domain.ArticleViewCount
import com.devfloor.hongit.core.articleviewcount.domain.ArticleViewCountRepository
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.board.domain.BoardRepository
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.hashtag.domain.Hashtag
import com.devfloor.hongit.core.option.domain.Option
import com.devfloor.hongit.core.option.domain.OptionRepository
import com.devfloor.hongit.core.option.domain.OptionType
import com.devfloor.hongit.core.user.domain.Email
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserRepository
import com.devfloor.hongit.core.user.domain.UserType
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyList
import org.mockito.Mockito.doNothing
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.util.Optional

@ExtendWith(MockitoExtension::class)
internal class ArticleServiceTest {
    @InjectMocks
    private lateinit var articleService: ArticleService

    @Mock
    private lateinit var articleRepository: ArticleRepository

    @Mock
    private lateinit var articleFavoriteRepository: ArticleFavoriteRepository

    @Mock
    private lateinit var articleHashtagRepository: ArticleHashtagRepository

    @Mock
    private lateinit var articleOptionRepository: ArticleOptionRepository

    @Mock
    private lateinit var boardRepository: BoardRepository

    @Mock
    private lateinit var optionRepository: OptionRepository

    @Mock
    private lateinit var articleViewCountRepository: ArticleViewCountRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var articleRepositoryCustom: ArticleRepositoryCustom

    @Mock
    private lateinit var articleHashtagService: ArticleHashtagService

    @Mock
    private lateinit var articleOptionService: ArticleOptionService

    @Mock
    private lateinit var hashtagService: HashtagService

    private lateinit var user: User

    private lateinit var board: Board

    private lateinit var article: Article

    private lateinit var option: Option

    private lateinit var articleOption: ArticleOption

    private lateinit var hashtag: Hashtag

    private lateinit var articleHashtag: ArticleHashtag

    private lateinit var articleFavorite: ArticleFavorite

    private lateinit var articleViewCount: ArticleViewCount

    @BeforeEach
    fun setUp() {
        articleService = ArticleService(
            articleRepository,
            articleFavoriteRepository,
            articleHashtagRepository,
            articleOptionRepository,
            boardRepository,
            optionRepository,
            articleViewCountRepository,
            userRepository,
            articleRepositoryCustom,
            articleHashtagService,
            articleOptionService,
            hashtagService,
        )

        user = User(
            id = 0,
            username = "username",
            password = "password",
            nickname = "nickname",
            email = Email.from("email@g.hongik.ac.kr"),
            type = UserType.GRADUATE,
            classOf = "B411158",
            image = "https://image.com",
            github = "github",
            blog = "blog",
            description = "test user description",
        )

        board = Board(id = 0, title = "course", type = BoardType.COURSE_BOARD)

        article = Article(
            id = 1,
            title = "test",
            anonymous = false,
            content = "test content",
            author = user,
            board = board,
        )

        option = Option("option", OptionType.COURSE_GROUP)

        articleOption = ArticleOption(article, option)

        hashtag = Hashtag(id = 1, name = "hashtag_name")

        articleHashtag = ArticleHashtag(article, hashtag)

        articleFavorite = ArticleFavorite(article, user, ArticleFavoriteType.FAVORITE)

        articleViewCount = ArticleViewCount(article, 5)
    }

    @Test
    internal fun `showByArticleId - id로 article 상세정보 조회에 성공한다`() {
        `when`(articleRepository.findById(any())).thenReturn(Optional.of(article))
        `when`(articleOptionRepository.findAllByArticle(any())).thenReturn(listOf(articleOption))
        `when`(articleHashtagRepository.findAllByArticle(any())).thenReturn(listOf(articleHashtag))
        `when`(articleFavoriteRepository.findAllByArticle(any())).thenReturn(listOf(articleFavorite))
        `when`(articleViewCountRepository.findByArticle(any())).thenReturn(Optional.of(articleViewCount))

        val articleResponse = articleService.showByArticleId(0)

        assertThat(articleResponse).isEqualTo(
            ArticleResponse(article, listOf(articleOption), listOf(articleHashtag), listOf(articleFavorite))
        )
    }

    @Test
    internal fun `showByArticleId - 조회수 객체를 새로 생성하고 id로 article 상세정보 조회에 성공한다`() {
        `when`(articleRepository.findById(any())).thenReturn(Optional.of(article))
        `when`(articleOptionRepository.findAllByArticle(any())).thenReturn(listOf(articleOption))
        `when`(articleHashtagRepository.findAllByArticle(any())).thenReturn(listOf(articleHashtag))
        `when`(articleFavoriteRepository.findAllByArticle(any())).thenReturn(listOf(articleFavorite))
        `when`(articleViewCountRepository.findByArticle(any())).thenReturn(Optional.empty())
        `when`(articleViewCountRepository.save(any())).thenReturn(articleViewCount)

        val articleResponse = articleService.showByArticleId(0)

        assertThat(articleResponse).isEqualTo(
            ArticleResponse(article, listOf(articleOption), listOf(articleHashtag), listOf(articleFavorite))
        )
    }

    @Test
    internal fun `showByArticleId - id에 해당하는 article이 존재하지 않는 경우 article 상세정보 조회에 실패한다`() {
        `when`(articleRepository.findById(any())).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Article이(가) 존재하지 않습니다 - ArticleId: 1"

        assertThatThrownBy { articleService.showByArticleId(1) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `showAllByBoardId - board id에 해당하는 article 리스트를 조회순으로 조회에 성공한다`() {
        `when`(boardRepository.findById(any())).thenReturn(Optional.of(board))
        val articles = listOf(article)
        `when`(articleRepositoryCustom.findAllByBoardOrderByViewCount(any(), any())).thenReturn(articles)
        val articleOptions = listOf(articleOption)
        `when`(articleOptionRepository.findAllByArticle(any())).thenReturn(articleOptions)
        val articleFavorites = listOf(articleFavorite)
        `when`(articleFavoriteRepository.findAllByArticle(any())).thenReturn(articleFavorites)
        val totalArticleCount = 1
        `when`(articleRepository.countAllByBoard(any())).thenReturn(totalArticleCount)
        val page = 1

        val articleFeedResponses = articleService.showAllByBoardId(0, page, 10, ArticleSortType.VIEW_COUNT)

        assertThat(articleFeedResponses).isEqualTo(
            articles.map {
                ArticleFeedResponse(
                    article = it,
                    articleOptions,
                    articleFavorites,
                    page,
                    totalArticleCount,
                )
            }
        )
    }

    @Test
    internal fun `showAllByBoardId - board id에 해당하는 article 리스트 조회순 조회시 존재하지 않는 경우 모든 게시물을 조회한다`() {
        `when`(boardRepository.findById(any())).thenReturn(Optional.of(board))
        `when`(articleRepositoryCustom.findAllByBoardOrderByViewCount(any(), any())).thenReturn(emptyList())
        val articles: Page<Article> = PageImpl(listOf(article))
        `when`(articleRepository.findAllByBoard(any(), any())).thenReturn(articles)
        val articleOptions = listOf(articleOption)
        `when`(articleOptionRepository.findAllByArticle(any())).thenReturn(articleOptions)
        val articleFavorites = listOf(articleFavorite)
        `when`(articleFavoriteRepository.findAllByArticle(any())).thenReturn(articleFavorites)
        val totalArticleCount = 1
        `when`(articleRepository.countAllByBoard(any())).thenReturn(totalArticleCount)
        val page = 1

        val articleFeedResponses = articleService.showAllByBoardId(0, page, 10, ArticleSortType.VIEW_COUNT)

        assertThat(articleFeedResponses).isEqualTo(
            articles.content.map {
                ArticleFeedResponse(
                    article = it,
                    articleOptions,
                    articleFavorites,
                    page,
                    totalArticleCount,
                )
            }
        )
    }

    @Test
    internal fun `showAllByBoardId - board id에 해당하는 article 리스트를 좋아요순으로 조회에 성공한다`() {
        `when`(boardRepository.findById(any())).thenReturn(Optional.of(board))
        val articles: Page<Article> = PageImpl(listOf(article))
        `when`(articleRepositoryCustom.findAllByBoardOrderByFavorite(any(), any())).thenReturn(articles)
        val articleOptions = listOf(articleOption)
        `when`(articleOptionRepository.findAllByArticle(any())).thenReturn(articleOptions)
        val articleFavorites = listOf(articleFavorite)
        `when`(articleFavoriteRepository.findAllByArticle(any())).thenReturn(articleFavorites)
        val totalArticleCount = 1
        `when`(articleRepository.countAllByBoard(any())).thenReturn(totalArticleCount)
        val page = 1

        val articleFeedResponses = articleService.showAllByBoardId(0, page, 10, ArticleSortType.FAVORITE)

        assertThat(articleFeedResponses).isEqualTo(
            articles.content.map {
                ArticleFeedResponse(
                    article = it,
                    articleOptions,
                    articleFavorites,
                    page,
                    totalArticleCount,
                )
            }
        )
    }

    @Test
    internal fun `showAllByBoardId - board id에 해당하는 article 리스트 좋아요순 조회시 존재하지 않는 경우 모든 게시물을 조회한다`() {
        `when`(boardRepository.findById(any())).thenReturn(Optional.of(board))
        `when`(articleRepositoryCustom.findAllByBoardOrderByFavorite(any(), any())).thenReturn(Page.empty())
        val articles: Page<Article> = PageImpl(listOf(article))
        `when`(articleRepository.findAllByBoard(any(), any())).thenReturn(articles)
        val articleOptions = listOf(articleOption)
        `when`(articleOptionRepository.findAllByArticle(any())).thenReturn(articleOptions)
        val articleFavorites = listOf(articleFavorite)
        `when`(articleFavoriteRepository.findAllByArticle(any())).thenReturn(articleFavorites)
        val totalArticleCount = 1
        `when`(articleRepository.countAllByBoard(any())).thenReturn(totalArticleCount)
        val page = 1

        val articleFeedResponses = articleService.showAllByBoardId(0, page, 10, ArticleSortType.FAVORITE)

        assertThat(articleFeedResponses).isEqualTo(
            articles.content.map {
                ArticleFeedResponse(
                    article = it,
                    articleOptions,
                    articleFavorites,
                    page,
                    totalArticleCount,
                )
            }
        )
    }

    @Test
    internal fun `showAllByBoardId - board id에 해당하는 article 리스트를 최신순으로 조회에 성공한다`() {
        `when`(boardRepository.findById(any())).thenReturn(Optional.of(board))
        val articles: Page<Article> = PageImpl(listOf(article))
        `when`(articleRepository.findAllByBoard(any(), any())).thenReturn(articles)
        val articleOptions = listOf(articleOption)
        `when`(articleOptionRepository.findAllByArticle(any())).thenReturn(articleOptions)
        val articleFavorites = listOf(articleFavorite)
        `when`(articleFavoriteRepository.findAllByArticle(any())).thenReturn(articleFavorites)
        val totalArticleCount = 1
        `when`(articleRepository.countAllByBoard(any())).thenReturn(totalArticleCount)
        val page = 1

        val articleFeedResponses = articleService.showAllByBoardId(0, page, 10, ArticleSortType.CREATED)

        assertThat(articleFeedResponses).isEqualTo(
            articles.content.map {
                ArticleFeedResponse(
                    article = it,
                    articleOptions,
                    articleFavorites,
                    page,
                    totalArticleCount,
                )
            }
        )
    }

    @Test
    internal fun `showByArticleId - id에 해당하는 board가 존재하지 않는 경우 article 리스트 조회에 실패한다`() {
        `when`(boardRepository.findById(any())).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Board이(가) 존재하지 않습니다 - BoardId: 1"

        assertThatThrownBy { articleService.showAllByBoardId(1, 1, 10) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `showTopFiveByFavorite - 좋아요 수 상위 5위까지의 article 리스트 조회에 성공한다`() {
        val articles = listOf(article)
        `when`(articleRepositoryCustom.findByFavoriteTopFive()).thenReturn(articles)
        `when`(articleFavoriteRepository.findAllByArticle(any())).thenReturn(listOf(articleFavorite))

        val articleHomeResponses = articleService.showTopFiveByFavorite()

        assertThat(articleHomeResponses).isEqualTo(articles.map { ArticleHomeResponse(it, listOf(articleFavorite)) })
    }

    @Test
    internal fun `showTopFiveByViewCount - 조회수 상위 5위까지의 article 리스트 조회에 성공한다`() {
        val articles = listOf(article)
        `when`(articleRepositoryCustom.findByViewCountTopFive()).thenReturn(articles)
        `when`(articleFavoriteRepository.findAllByArticle(any())).thenReturn(listOf(articleFavorite))

        val articleHomeResponses = articleService.showTopFiveByViewCount()

        assertThat(articleHomeResponses).isEqualTo(articles.map { ArticleHomeResponse(it, listOf(articleFavorite)) })
    }

    @Test
    internal fun `showTopFiveByBoard - 최근 생성된 5개의 article 리스트 조회에 성공한다`() {
        val articles = listOf(article)
        `when`(articleRepository.findTop5ByBoardOrderByCreatedAtDesc(any())).thenReturn(articles)
        `when`(articleFavoriteRepository.findAllByArticle(any())).thenReturn(listOf(articleFavorite))

        val articleHomeResponses = articleService.showTopFiveByBoard(board)

        assertThat(articleHomeResponses).isEqualTo(articles.map { ArticleHomeResponse(it, listOf(articleFavorite)) })
    }

    @Test
    internal fun `showAllByNickname - nickname에 해당하는 유저의 모든 article 리스트 조회에 성공한다`() {
        `when`(userRepository.findByNickname(any())).thenReturn(Optional.of(user))
        val articles: Page<Article> = PageImpl(listOf(article))
        `when`(articleRepository.findAllByAuthor(any(), any())).thenReturn(articles)
        val articleOptions = listOf(articleOption)
        `when`(articleOptionRepository.findAllByArticle(any())).thenReturn(articleOptions)
        val articleFavorites = listOf(articleFavorite)
        `when`(articleFavoriteRepository.findAllByArticle(any())).thenReturn(articleFavorites)
        val totalArticleCount = 1
        `when`(articleRepository.countAllByAuthor(any())).thenReturn(totalArticleCount)
        val page = 1

        val articleFeedResponses = articleService.showAllByNickname("nickname", page, 10, user)

        assertThat(articleFeedResponses).isEqualTo(
            articles.content.map {
                ArticleFeedResponse(it, articleOptions, articleFavorites, page, totalArticleCount)
            }
        )
    }

    @Test
    internal fun `showAllByNickname - nickname에 해당하는 유저가 존재하지 않는 경우 article 리스트 조회에 성공한다`() {
        `when`(userRepository.findByNickname(any())).thenReturn(Optional.empty())
        val errorMessage = "nickname에 해당하는 User이(가) 존재하지 않습니다 - nickname: nickname"

        assertThatThrownBy { articleService.showAllByNickname("nickname", 1, 10, user) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `showAllByFavoritedUserId - 유저가 좋아요를 누른 게시글 조회에 성공한다`() {
        `when`(userRepository.findById(any())).thenReturn(Optional.of(user))
        val articleFavorites = listOf(articleFavorite)
        `when`(articleFavoriteRepository.findAllByUser(any())).thenReturn(articleFavorites)
        val articles: Page<Article> = PageImpl(listOf(article))
        `when`(articleRepository.findAllByIdIn(any(), any())).thenReturn(articles)
        val articleOptions = listOf(articleOption)
        `when`(articleOptionRepository.findAllByArticle(any())).thenReturn(articleOptions)
        `when`(articleFavoriteRepository.findAllByArticle(any())).thenReturn(articleFavorites)
        val totalArticleCount = 1
        `when`(articleRepository.countAllByIdIn(any())).thenReturn(totalArticleCount)
        val page = 1

        val articleFeedResponses = articleService.showAllByFavoritedUserId(0, page, 10)

        assertThat(articleFeedResponses).isEqualTo(
            articles.content.map {
                ArticleFeedResponse(it, articleOptions, articleFavorites, page, totalArticleCount)
            }
        )
    }

    @Test
    internal fun `showAllByFavoritedUserId - id에 해당하는 유저가 존재하지 않는 경우 유저가 좋아요를 누른 게시글 조회에 실패한다`() {
        `when`(userRepository.findById(any())).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 User이(가) 존재하지 않습니다 - UserId: 0"

        assertThatThrownBy { articleService.showAllByFavoritedUserId(0, 1, 10) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `create - 게시글 생성에 성공한다`() {
        `when`(boardRepository.findById(any())).thenReturn(Optional.of(board))
        `when`(articleRepository.save(any())).thenReturn(article)
        `when`(optionRepository.findAllById(any())).thenReturn(listOf(option))
        `when`(articleOptionRepository.saveAll(anyList())).thenReturn(listOf(articleOption))
        `when`(hashtagService.createAllByNames(anyList())).thenReturn(listOf(hashtag))
        `when`(articleHashtagRepository.saveAll(anyList())).thenReturn(listOf(articleHashtag))
        val articleCreateRequest = ArticleCreateRequest(listOf(0), "title", true, "content", listOf("hashtag"), 0)

        val articleId = articleService.create(articleCreateRequest, user)

        assertThat(articleId).isEqualTo(article.id)
    }

    @Test
    internal fun `create - id에 해당하는 board가 존재하지 않는 경우 게시글 생성에 실패한다`() {
        `when`(boardRepository.findById(any())).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Board이(가) 존재하지 않습니다 - BoardId: 0"
        val articleCreateRequest = ArticleCreateRequest(listOf(0), "title", true, "content", listOf("hashtag"), 0)

        assertThatThrownBy { articleService.create(articleCreateRequest, user) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `modifyByArticleId - id에 해당하는 게시글 수정에 성공한다`() {
        `when`(articleRepository.findById(any())).thenReturn(Optional.of(article))
        `when`(hashtagService.createAllByNames(anyList())).thenReturn(listOf(hashtag))
        doNothing().`when`(articleHashtagService).modifyByArticle(any(), any())
        `when`(optionRepository.findAllById(any())).thenReturn(listOf(option))
        doNothing().`when`(articleOptionService).modifyByArticle(any(), any())
        val articleModifyRequest = ArticleModifyRequest(listOf(0), "title", "content", listOf("hashtag"))

        val result = articleService.modifyByArticleId(0, articleModifyRequest)

        assertThat(result).isEqualTo(Unit)
    }

    @Test
    internal fun `modifyByArticleId - id에 해당하는 게시글이 존재하지 않는 경우 게시글 수정에 실패한다`() {
        `when`(articleRepository.findById(any())).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Article이(가) 존재하지 않습니다 - ArticleId: 0"
        val articleModifyRequest = ArticleModifyRequest(listOf(0), "title", "content", listOf("hashtag"))

        assertThatThrownBy{ articleService.modifyByArticleId(0, articleModifyRequest) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `destroyByArticleId - id에 해당하는 게시글 삭제에 성공한다`() {
        `when`(articleRepository.findById(any())).thenReturn(Optional.of(article))
        doNothing().`when`(articleOptionRepository).deleteAllByArticle(any())
        doNothing().`when`(articleHashtagRepository).deleteAllByArticle(any())
        doNothing().`when`(articleFavoriteRepository).deleteAllByArticle(any())
        doNothing().`when`(articleRepository).delete(any())
        doNothing().`when`(articleViewCountRepository).deleteByArticle(any())

        val result = articleService.destroyByArticleId(0)

        assertThat(result).isEqualTo(Unit)
    }

    @Test
    internal fun `destroyByArticleId - id에 해당하는 게시글이 존재하지 않는 경우 게시글 삭제에 실패한다`() {
        `when`(articleRepository.findById(any())).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Article이(가) 존재하지 않습니다 - ArticleId: 0"

        assertThatThrownBy{ articleService.destroyByArticleId(0) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }
}
