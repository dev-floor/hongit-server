package com.devfloor.hongit.api.commentfavorite

import com.devfloor.hongit.api.commentfavorite.application.CommentFavoriteService
import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.support.TestFixtures
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.comment.domain.Comment
import com.devfloor.hongit.core.comment.domain.CommentRepository
import com.devfloor.hongit.core.commentfavorite.domain.CommentFavorite
import com.devfloor.hongit.core.commentfavorite.domain.CommentFavoriteRepository
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
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class CommentFavoriteServiceTest {
    @Mock
    private lateinit var commentRepository: CommentRepository

    @Mock
    private lateinit var commentFavoriteRepository: CommentFavoriteRepository

    private lateinit var commentFavoriteService: CommentFavoriteService

    private lateinit var user: User

    private lateinit var comment: Comment

    private lateinit var commentFavorite: CommentFavorite

    private lateinit var article: Article

    private lateinit var board: Board

    @BeforeEach
    internal fun setUp() {
        commentFavoriteService = CommentFavoriteService(commentRepository, commentFavoriteRepository)

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

        comment = Comment(
            id = 1,
            article = TestFixtures.ArticleFixture.ARTICLE_1,
            author = user,
            anonymous = false,
            content = "comment 1",
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

        commentFavorite = CommentFavorite(comment, user)
    }

    @Test
    internal fun `create - CommentFavorite 객체 생성에 성공한다`() {
        `when`(commentRepository.findById(comment.id)).thenReturn(Optional.of(comment))
        `when`(commentFavoriteRepository.save(any())).thenReturn(commentFavorite)

        val commentFavoriteId = commentFavoriteService.create(comment.id, user)

        assertThat(commentFavoriteId).isEqualTo(commentFavorite.id)
    }

    @Test
    internal fun `create - id에 해당하는 Comment 객체가 존재하지 않는 경우 CommentFavorite 객체 생성에 실패한다`() {
        `when`(commentRepository.findById(comment.id)).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Comment이(가) 존재하지 않습니다 - CommentId: 1"

        assertThatThrownBy { commentFavoriteService.create(comment.id, user) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `create - CommentFavorite 객체가 이미 존재하는 경우 생성에 실패한다`() {
        `when`(commentRepository.findById(comment.id)).thenReturn(Optional.of(comment))
        `when`(commentFavoriteRepository.existsByCommentAndUser(comment, user)).thenReturn(true)
        val errorMessage = "해당 comment에 대한 좋아요가 이미 존재합니다."

        assertThatThrownBy { commentFavoriteService.create(comment.id, user) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `destroy - CommentFavorite 객체 삭제에 성공한다`() {
        `when`(commentRepository.findById(comment.id)).thenReturn(Optional.of(comment))
        doNothing().`when`(commentFavoriteRepository).deleteByCommentAndUser(comment, user)

        val result = commentFavoriteService.destroy(comment.id, user)

        assertThat(result).isEqualTo(Unit)
        verify(commentFavoriteRepository).deleteByCommentAndUser(comment, user)
    }

    @Test
    internal fun `create - id에 해당하는 Comment 객체가 존재하지 않는 경우 CommentFavorite 객체 삭제에 실패한다`() {
        `when`(commentRepository.findById(comment.id)).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Comment이(가) 존재하지 않습니다 - CommentId: 1"

        assertThatThrownBy { commentFavoriteService.destroy(comment.id, user) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }
}
