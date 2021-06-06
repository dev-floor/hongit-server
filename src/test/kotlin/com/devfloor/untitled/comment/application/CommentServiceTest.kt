package com.devfloor.untitled.comment.application

import com.devfloor.untitled.comment.application.request.CommentCreateRequest
import com.devfloor.untitled.common.exception.EntityNotFoundException
import com.devfloor.untitled.user.domain.User
import com.devfloor.untitled.user.domain.UserRepository
import com.devfloor.untitled.user.domain.UserType
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class CommentServiceTest {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var commentService: CommentService

    @Test
    internal fun `create - articleId에 해당하는 게시글이 없을 경우 예외 발생`() {
        // given
        val author = User(
            nickname = "nickname",
            type = UserType.STUDENT,
            image = "image",
            classOf = "classOf",
            github = "github",
            blog = "blog",
            description = "description"
        ).let { userRepository.save(it) }

        val request = CommentCreateRequest(
            articleId = 1,
            anonymous = false,
            content = "content"
        )

        // when - then
        assertThatThrownBy { commentService.create(author, request) }
            .isInstanceOf(EntityNotFoundException::class.java)
    }
}
