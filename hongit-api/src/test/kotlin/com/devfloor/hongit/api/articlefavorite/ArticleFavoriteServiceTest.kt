package com.devfloor.hongit.api.articlefavorite

import com.devfloor.hongit.api.articlefavorite.application.ArticleFavoriteService
import com.devfloor.hongit.api.articlefavorite.application.request.ArticleFavoriteCreateRequest
import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.article.domain.ArticleRepository
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavorite
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteRepository
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.user.domain.Email
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserType
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.`when`
import org.mockito.BDDMockito.doNothing
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
internal class ArticleFavoriteServiceTest {
    @Mock
    private lateinit var articleFavoriteRepository: ArticleFavoriteRepository

    @Mock
    private lateinit var articleRepository: ArticleRepository

    private lateinit var articleFavoriteService: ArticleFavoriteService

    private lateinit var article: Article

    private lateinit var user: User

    private lateinit var articleFavorite: ArticleFavorite

    private lateinit var board: Board

    private lateinit var articleFavoriteCreateRequest: ArticleFavoriteCreateRequest

    @BeforeEach
    internal fun setUp() {
        articleFavoriteService = ArticleFavoriteService(articleFavoriteRepository, articleRepository)

        user = User(
            id = 1,
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

        articleFavorite = ArticleFavorite(
            article = article,
            user = user,
            type = ArticleFavoriteType.FAVORITE
        )

        articleFavoriteCreateRequest = ArticleFavoriteCreateRequest(
            articleId = article.id,
            type = ArticleFavoriteType.FAVORITE
        )
    }

    @Test
    internal fun `create - ArticleFavorite 객체 생성에 성공한다`() {
        `when`(articleRepository.findById(article.id)).thenReturn(Optional.of(article))
        `when`(articleFavoriteRepository.save(any())).thenReturn(articleFavorite)

        val articleFavoriteId = articleFavoriteService.create(articleFavoriteCreateRequest, user)

        assertThat(articleFavoriteId).isEqualTo(articleFavorite.id)
    }

    @Test
    internal fun `create - id에 해당하는 Article 객체가 존재하지 않는 경우 ArticleFavorite 객체 생성에 실패한다`() {
        `when`(articleRepository.findById(article.id)).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Article이(가) 존재하지 않습니다 - ArticleId: 1"

        assertThatThrownBy { articleFavoriteService.create(articleFavoriteCreateRequest, user) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `create - ArticleFavorite 객체가 이미 존재하는 경우 생성에 실패한다`() {
        `when`(articleRepository.findById(article.id)).thenReturn(Optional.of(article))
        `when`(articleFavoriteRepository.existsByArticleAndUser(article, user)).thenReturn(true)
        val errorMessage = "해당 article에 대한 좋아요가 이미 존재합니다."

        assertThatThrownBy { articleFavoriteService.create(articleFavoriteCreateRequest, user) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `destroy - ArticleFavorite 객체 삭제에 성공한다`() {
        `when`(articleRepository.findById(article.id)).thenReturn(Optional.of(article))
        doNothing().`when`(articleFavoriteRepository).deleteByArticleAndUser(article, user)

        val result = articleFavoriteService.destroy(article.id, user)

        assertThat(result).isEqualTo(Unit)
        verify(articleFavoriteRepository).deleteByArticleAndUser(article, user)
    }

    @Test
    internal fun `create - id에 해당하는 Article 객체가 존재하지 않는 경우 ArticleFavorite 객체 삭제에 실패한다`() {
        `when`(articleRepository.findById(article.id)).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Article이(가) 존재하지 않습니다 - ArticleId: 1"

        assertThatThrownBy { articleFavoriteService.destroy(article.id, user) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }
}
