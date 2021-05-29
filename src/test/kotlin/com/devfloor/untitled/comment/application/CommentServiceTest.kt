package com.devfloor.untitled.comment.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.article.domain.ArticleRepository
import com.devfloor.untitled.comment.application.request.CommentRequest
import com.devfloor.untitled.user.domain.User
import com.devfloor.untitled.user.domain.UserRepository
import com.devfloor.untitled.user.domain.UserType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class CommentServiceTest {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var articleRepository: ArticleRepository

    @Autowired
    lateinit var commentService: CommentService

    @Test
    internal fun `create - 댓글 생성 테스트`() {
        // given
        val user = User(
            nickname = "test",
            type = UserType.STUDENT,
            image = "image",
            classOf = "B400000",
            github = "github",
            blog = "blog",
            description = "description"
        ).also { userRepository.save(it) }

        val article = Article(
            title = "title",
            anonymous = false,
            content = "content",
            author = user
        ).also { articleRepository.save(it) }

        // when
        val request = CommentRequest(anonymous = false, content = "content")
        val actual = commentService.create(
            articleId = article.id,
            author = user,
            request = request
        )

        // then
        assertAll(
            { assertThat(actual.id).isNotNull },
            { assertThat(actual.anonymous).isEqualTo(request.anonymous) },
            { assertThat(actual.content).isEqualTo(request.content) }
        )
    }
}
