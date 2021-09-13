package com.devfloor.hongit.api.support

import com.devfloor.hongit.api.article.application.request.ArticleCreateRequest
import com.devfloor.hongit.api.article.application.request.ArticleModifyRequest
import com.devfloor.hongit.api.article.application.response.ArticleFeedResponse
import com.devfloor.hongit.api.article.application.response.ArticleResponse
import com.devfloor.hongit.api.article.domain.ArticleSortType
import com.devfloor.hongit.api.articlefavorite.application.request.ArticleFavoriteCreateRequest
import com.devfloor.hongit.api.board.application.response.BoardResponse
import com.devfloor.hongit.api.board.application.response.BoardSimpleResponse
import com.devfloor.hongit.api.option.application.response.OptionResponse
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.ARTICLE_1
import com.devfloor.hongit.api.support.TestFixtures.BoardFixture.BOARD_1
import com.devfloor.hongit.api.support.TestFixtures.CourseFixture.COURSE_1
import com.devfloor.hongit.api.support.TestFixtures.HashtagFixture.HASHTAG_1
import com.devfloor.hongit.api.support.TestFixtures.HashtagFixture.HASHTAG_2
import com.devfloor.hongit.api.support.TestFixtures.HashtagFixture.HASHTAG_MODIFY_1
import com.devfloor.hongit.api.support.TestFixtures.HashtagFixture.HASHTAG_MODIFY_2
import com.devfloor.hongit.api.support.TestFixtures.OptionFixture.OPTION_1
import com.devfloor.hongit.api.support.TestFixtures.OptionFixture.OPTION_2
import com.devfloor.hongit.api.support.TestFixtures.OptionFixture.OPTION_MODIFY_1
import com.devfloor.hongit.api.support.TestFixtures.OptionFixture.OPTION_MODIFY_2
import com.devfloor.hongit.api.support.TestFixtures.OptionFixture.OPTION_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.ProfessorFixture.PROFESSOR_1
import com.devfloor.hongit.api.support.TestFixtures.SubjectFixture.SUBJECT_1
import com.devfloor.hongit.api.support.TestFixtures.UserFixture.USER_1
import com.devfloor.hongit.api.user.application.request.JoinRequest
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavorite
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.course.domain.Course
import com.devfloor.hongit.core.course.domain.Grade
import com.devfloor.hongit.core.course.domain.OpeningSemester
import com.devfloor.hongit.core.course.domain.Schedule
import com.devfloor.hongit.core.course.domain.Semester
import com.devfloor.hongit.core.course.domain.Timetable
import com.devfloor.hongit.core.hashtag.domain.Hashtag
import com.devfloor.hongit.core.option.domain.Option
import com.devfloor.hongit.core.option.domain.OptionType
import com.devfloor.hongit.core.professor.domain.Professor
import com.devfloor.hongit.core.subject.domain.Subject
import com.devfloor.hongit.core.user.domain.Email
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserType
import java.time.LocalDateTime
import java.time.Year

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
        val ARTICLE_CREATE_REQUEST_1 = ArticleCreateRequest(
            optionIds = listOf(OPTION_1.id, OPTION_2.id),
            title = ARTICLE_1.title,
            anonymous = ARTICLE_1.anonymous,
            content = ARTICLE_1.content,
            hashtagNames = listOf(HASHTAG_1.name, HASHTAG_2.name),
            boardId = ARTICLE_1.board.id
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

        val ARTICLE_FEED_RESPONSE_1 = ArticleFeedResponse(
            id = ARTICLE_1.id,
            options = listOf(OPTION_RESPONSE_1),
            title = ARTICLE_1.title,
            anonymous = ARTICLE_1.anonymous,
            author = ProfileResponse(ARTICLE_1.author),
            content = ARTICLE_1.content,
            favoriteCount = 0,
            wonderCount = 1,
            clipCount = 2,
            page = 1,
            totalArticleCount = 120,
            createdAt = TEST_CREATED_AT,
            modifiedAt = TEST_MODIFIED_AT,
        )

        val ARTICLE_MODIFY_REQUEST_1 = ArticleModifyRequest(
            optionIds = listOf(OPTION_MODIFY_1.id, OPTION_MODIFY_2.id),
            title = "modify article title 111",
            content = "modify article content 111",
            hashtagNames = listOf(HASHTAG_MODIFY_1.name, HASHTAG_MODIFY_2.name)
        )

        val ARTICLE_ID_1: Long = 1
    }

    object BoardFixture {
        val BOARD_1 = Board(
            id = 1,
            title = "board 1",
            type = BoardType.COURSE_BOARD,
        )

        val BOARD_2 = Board(
            id = 2,
            title = "board 2",
            type = BoardType.QNA_BOARD,
        )

        val BOARD_RESPONSE_1 = BoardResponse(
            board = BOARD_1,
            options = listOf(OPTION_1, OPTION_2),
            course = COURSE_1,
        )

        val BOARD_SIMPLE_RESPONSE_1 = BoardSimpleResponse(
            board = BOARD_1,
            grade = COURSE_1.grade,
        )
    }

    object CourseFixture {
        val COURSE_1 = Course(
            id = 1,
            code = "0000",
            openingSemester = OpeningSemester(Year.parse("2021"), Semester.FIRST_SEMESTER),
            professor = PROFESSOR_1,
            subject = SUBJECT_1,
            grade = Grade.FRESHMAN,
            option = OPTION_2,
            timetable = Timetable(listOf(Schedule.from("월2"), Schedule.from("월3"), Schedule.from("수3"))),
            board = BOARD_1,
        )
    }

    object OptionFixture {
        val OPTION_1 = Option(
            id = 1,
            text = "option 1",
            type = OptionType.ARTICLE_KIND
        )

        val OPTION_2 = Option(
            id = 2,
            text = "option 2",
            type = OptionType.COURSE_GROUP,
        )

        val OPTION_3 = Option(
            id = 3,
            text = "option 3",
            type = OptionType.ARTICLE_KIND,
        )

        val OPTION_MODIFY_1 = Option(
            id = 4,
            text = "option modify 1",
            type = OptionType.ARTICLE_KIND
        )

        val OPTION_MODIFY_2 = Option(
            id = 5,
            text = "option modify 2",
            type = OptionType.COURSE_GROUP,
        )
        val OPTION_RESPONSE_1 = OptionResponse(OPTION_1)
    }

    object SubjectFixture {
        val SUBJECT_1 = Subject(id = 1, name = "subject 1")
    }

    object ProfessorFixture {
        val PROFESSOR_1 = Professor(
            id = 1,
            name = "professor 1",
            email = "professor@gmail.com",
        )
    }

    object ArticleSortTypeFixture {
        val ARTICLE_SORT_TYPE_1 = ArticleSortType.CREATED
    }

    object ArticleFavoriteFixture {
        val ARTICLE_FAVORITE_1 = ArticleFavorite(
            article = ARTICLE_1,
            user = USER_1,
            type = ArticleFavoriteType.FAVORITE
        )

        val ARTICLE_FAVORITE_CREATE_REQUEST_1 = ArticleFavoriteCreateRequest(
            articleId = 1,
            type = ArticleFavoriteType.FAVORITE
        )
    }

    object FavoriteTypeFixture {
        val FAVORITE_TYPE_1 = ArticleFavoriteType.FAVORITE
    }

    object HashtagFixture {
        val HASHTAG_1 = Hashtag(
            id = 1,
            name = "hashtagname1"
        )

        val HASHTAG_2 = Hashtag(
            id = 2,
            name = "hashtagname2"
        )

        val HASHTAG_MODIFY_1 = Hashtag(
            id = 3,
            name = "hashtagname modified 3"
        )

        val HASHTAG_MODIFY_2 = Hashtag(
            id = 4,
            name = "hashtagname modified 4"
        )
    }
}
