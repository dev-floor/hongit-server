package com.devfloor.hongit.api.comment.application

import com.devfloor.hongit.api.comment.application.request.CommentCreateRequest
import com.devfloor.hongit.api.comment.application.request.CommentModifyRequest
import com.devfloor.hongit.api.comment.application.response.CommentInProfileResponse
import com.devfloor.hongit.api.comment.application.response.CommentResponse
import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.ARTICLE_1
import com.devfloor.hongit.api.support.TestFixtures.CommentFixture.COMMENT_1
import com.devfloor.hongit.api.support.TestFixtures.CommentFixture.COMMENT_2
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.USER_1
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.article.domain.ArticleRepository
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.comment.domain.Comment
import com.devfloor.hongit.core.comment.domain.CommentRepository
import com.devfloor.hongit.core.commentfavorite.domain.CommentFavoriteRepository
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
import org.mockito.Mockito.doNothing
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
internal class CommentServiceTest {
    @InjectMocks
    private lateinit var commentService: CommentService

    @Mock
    private lateinit var articleRepository: ArticleRepository

    @Mock
    private lateinit var commentRepository: CommentRepository

    @Mock
    private lateinit var commentFavoriteRepository: CommentFavoriteRepository

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var article: Article

    private lateinit var user: User

    private lateinit var board: Board

    private lateinit var comment: Comment

    @BeforeEach
    fun setUp() {
        commentService = CommentService(
            articleRepository,
            commentRepository,
            commentFavoriteRepository,
            userRepository
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

        comment = Comment(
            id = 1,
            article = article,
            author = user,
            anonymous = false,
            content = "comment 1",
        )
    }

    @Test
    internal fun `showAllByArticleId - articleId로 comment 리스트 조회에 성공한다`() {
        `when`(articleRepository.findById(any())).thenReturn(Optional.of(ARTICLE_1))
        val comments = listOf(COMMENT_1, COMMENT_2)
        `when`(commentRepository.findAllByArticle(ARTICLE_1)).thenReturn(comments)
        comments.forEach { `when`(commentFavoriteRepository.countAllByComment(it)).thenReturn(1) }
        val commentResponses = comments.map { CommentResponse(it, 1) }

        val result = commentService.showAllByArticleId(1)

        assertThat(result).isEqualTo(commentResponses)
    }

    @Test
    internal fun `showAllByArticleId - id에 해당하는 Article 객체가 존재하지 않는 경우 comment 리스트 조회에 실패한다`() {
        `when`(articleRepository.findById(any())).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Article이(가) 존재하지 않습니다 - ArticleId: 1"

        assertThatThrownBy { commentService.showAllByArticleId(1) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `showAllByNickname - nickname으로 comment 리스트 조회에 성공한다`() {
        `when`(userRepository.findByNickname(any())).thenReturn(Optional.of(USER_1))
        val comments = listOf(COMMENT_1, COMMENT_2)
        `when`(commentRepository.findAllByAuthor(USER_1)).thenReturn(comments)
        comments.forEach { `when`(commentFavoriteRepository.countAllByComment(it)).thenReturn(1) }
        val commentInProfileResponses = comments.map { CommentInProfileResponse(it, 1) }

        val result = commentService.showAllByNickname("nickname")

        assertThat(result).isEqualTo(commentInProfileResponses)
    }

    @Test
    internal fun `showAllByNickname - nickname에 해당하는 Article 객체가 존재하지 않는 경우 comment 리스트 조회에 실패한다`() {
        `when`(userRepository.findByNickname(any())).thenReturn(Optional.empty())
        val errorMessage = "nickname에 해당하는 User이(가) 존재하지 않습니다 - nickname: nickname"

        assertThatThrownBy { commentService.showAllByNickname("nickname") }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `create - comment 생성에 성공한다`() {
        `when`(articleRepository.findById(any())).thenReturn(Optional.of(article))
        `when`(commentRepository.save(any())).thenReturn(comment)

        val commentResponse = commentService.create(user, CommentCreateRequest(1, true, "content"))

        assertThat(commentResponse).isEqualTo(CommentResponse(comment))
    }

    @Test
    internal fun `create - id에 해당하는 Article 객체가 존재하지 않는 경우 comment 생성에 실패한다`() {
        `when`(articleRepository.findById(any())).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Article이(가) 존재하지 않습니다 - ArticleId: 1"

        assertThatThrownBy { commentService.create(user, CommentCreateRequest(1, true, "content")) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `modifyByCommentId - comment 정보 수정에 성공한다`() {
        `when`(commentRepository.findById(any())).thenReturn(Optional.of(comment))

        val commentResponse = commentService.modifyByCommentId(1, CommentModifyRequest("new_content"))

        assertThat(commentResponse).isEqualTo(CommentResponse(comment))
    }

    @Test
    internal fun `modifyByCommentId - id에 해당하는 Comment 객체가 존재하지 않는 경우 comment 정보 수정에 실패한다`() {
        `when`(commentRepository.findById(any())).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Comment이(가) 존재하지 않습니다 - CommentId: 1"

        assertThatThrownBy { commentService.modifyByCommentId(1, CommentModifyRequest("new_content")) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `destroyByCommentId - comment 삭제에 성공한다`() {
        `when`(commentRepository.findById(any())).thenReturn(Optional.of(comment))
        doNothing().`when`(commentFavoriteRepository).deleteAllByComment(any())
        doNothing().`when`(commentRepository).delete(any())

        val result = commentService.destroyByCommentId(1)

        assertThat(result).isEqualTo(Unit)
    }

    @Test
    internal fun `destroyByCommentId - id에 해당하는 Comment 객체가 존재하지 않는 경우 comment 삭제에 실패한다`() {
        `when`(commentRepository.findById(any())).thenReturn(Optional.of(comment))

        val result = commentService.destroyByCommentId(1)

        assertThat(result).isEqualTo(Unit)
    }
}
