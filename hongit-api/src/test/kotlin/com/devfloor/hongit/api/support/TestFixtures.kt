package com.devfloor.hongit.api.support

import com.devfloor.hongit.api.article.application.response.ArticleResponse
import com.devfloor.hongit.api.common.application.response.BaseEnumResponse
import com.devfloor.hongit.api.option.application.response.OptionResponse
import com.devfloor.hongit.api.support.TestFixtures.BoardFixture.BOARD_1
import com.devfloor.hongit.api.support.TestFixtures.OptionFixture.OPTION_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.USER_1
import com.devfloor.hongit.api.user.application.request.JoinRequest
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.option.domain.OptionType
import com.devfloor.hongit.core.user.domain.Email
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserType
import java.time.LocalDateTime

object TestFixtures {
    private val TEST_CREATED_AT = LocalDateTime.of(2021, 8, 1, 12, 0, 0)
    private val TEST_MODIFIED_AT = LocalDateTime.of(2021, 8, 1, 12, 10, 0)

    object UserFixture {
        val JOIN_REQUEST_1 = JoinRequest(
            username = "username",
            nickname = "nickname",
            password = "password",
            checkedPassword = "password",
            email = "email@g.hongik.ac.kr",
            type = UserType.STUDENT,
            classOf = "B411158",
            approved = true,
        )

        val USER_1 = User(
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
    }

    object ArticleFixture {
        val ARTICLE_1 = Article(
            id = 1,
            title = "test",
            anonymous = false,
            content = "test content",
            author = USER_1,
            board = BOARD_1,
        )

        val ARTICLE_RESPONSE_1 = ArticleResponse(
            id = ARTICLE_1.id,
            options = listOf(OPTION_RESPONSE_1),
            title = ARTICLE_1.title,
            anonymous = ARTICLE_1.anonymous,
            author = ProfileResponse(ARTICLE_1.author),
            content = ARTICLE_1.content,
            hashtags = listOf("article", "1", "hashtag"),
            favoriteCount = 0,
            wonderCount = 1,
            clipCount = 2,
            createdAt = TEST_CREATED_AT,
            modifiedAt = TEST_MODIFIED_AT,
        )
    }

    object BoardFixture {
        val BOARD_1 = Board("title", BoardType.COURSE_BOARD)
    }

    object OptionFixture {
        val OPTION_RESPONSE_1 = OptionResponse(
            id = 1,
            text = "option 1",
            type = BaseEnumResponse(OptionType.ARTICLE_KIND)
        )
    }
}
