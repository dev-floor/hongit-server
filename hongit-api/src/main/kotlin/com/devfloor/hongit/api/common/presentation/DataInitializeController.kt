package com.devfloor.hongit.api.common.presentation

import com.devfloor.hongit.api.common.presentation.DataFixture.ArticleFavorites.TEST_ARTICLE_FAVORITES
import com.devfloor.hongit.api.common.presentation.DataFixture.ArticleHashtags.TEST_ARTICLE_HASHTAGS
import com.devfloor.hongit.api.common.presentation.DataFixture.ArticleOptions.TEST_ARTICLE_OPTIONS
import com.devfloor.hongit.api.common.presentation.DataFixture.Articles.TEST_ARTICLES
import com.devfloor.hongit.api.common.presentation.DataFixture.Articles.TEST_ARTICLE_1
import com.devfloor.hongit.api.common.presentation.DataFixture.Articles.TEST_ARTICLE_2
import com.devfloor.hongit.api.common.presentation.DataFixture.Articles.TEST_ARTICLE_3
import com.devfloor.hongit.api.common.presentation.DataFixture.Articles.TEST_ARTICLE_4
import com.devfloor.hongit.api.common.presentation.DataFixture.BoardOptions.TEST_BOARD_OPTIONS
import com.devfloor.hongit.api.common.presentation.DataFixture.Boards.TEST_BOARDS
import com.devfloor.hongit.api.common.presentation.DataFixture.Boards.TEST_BOARD_1
import com.devfloor.hongit.api.common.presentation.DataFixture.Boards.TEST_BOARD_2
import com.devfloor.hongit.api.common.presentation.DataFixture.Boards.TEST_BOARD_3
import com.devfloor.hongit.api.common.presentation.DataFixture.Boards.TEST_BOARD_4
import com.devfloor.hongit.api.common.presentation.DataFixture.Boards.TEST_BOARD_5
import com.devfloor.hongit.api.common.presentation.DataFixture.Boards.TEST_BOARD_6
import com.devfloor.hongit.api.common.presentation.DataFixture.Comments.TEST_COMMENTS
import com.devfloor.hongit.api.common.presentation.DataFixture.Courses.TEST_COURSES
import com.devfloor.hongit.api.common.presentation.DataFixture.Hashtags.TEST_HASHTAGS
import com.devfloor.hongit.api.common.presentation.DataFixture.Hashtags.TEST_HASHTAG_1
import com.devfloor.hongit.api.common.presentation.DataFixture.Hashtags.TEST_HASHTAG_2
import com.devfloor.hongit.api.common.presentation.DataFixture.Hashtags.TEST_HASHTAG_3
import com.devfloor.hongit.api.common.presentation.DataFixture.Hashtags.TEST_HASHTAG_4
import com.devfloor.hongit.api.common.presentation.DataFixture.Hashtags.TEST_HASHTAG_5
import com.devfloor.hongit.api.common.presentation.DataFixture.Hashtags.TEST_HASHTAG_6
import com.devfloor.hongit.api.common.presentation.DataFixture.Options.TEST_OPTIONS
import com.devfloor.hongit.api.common.presentation.DataFixture.Options.TEST_OPTION_1
import com.devfloor.hongit.api.common.presentation.DataFixture.Options.TEST_OPTION_2
import com.devfloor.hongit.api.common.presentation.DataFixture.Options.TEST_OPTION_3
import com.devfloor.hongit.api.common.presentation.DataFixture.Options.TEST_OPTION_4
import com.devfloor.hongit.api.common.presentation.DataFixture.Options.TEST_OPTION_5
import com.devfloor.hongit.api.common.presentation.DataFixture.Options.TEST_OPTION_6
import com.devfloor.hongit.api.common.presentation.DataFixture.Options.TEST_OPTION_7
import com.devfloor.hongit.api.common.presentation.DataFixture.Professors.TEST_PROFESSORS
import com.devfloor.hongit.api.common.presentation.DataFixture.Professors.TEST_PROFESSOR_1
import com.devfloor.hongit.api.common.presentation.DataFixture.Professors.TEST_PROFESSOR_2
import com.devfloor.hongit.api.common.presentation.DataFixture.Subjects.TEST_SUBJECTS
import com.devfloor.hongit.api.common.presentation.DataFixture.Subjects.TEST_SUBJECT_1
import com.devfloor.hongit.api.common.presentation.DataFixture.Users.TEST_USERS
import com.devfloor.hongit.api.common.presentation.DataFixture.Users.TEST_USER_1
import com.devfloor.hongit.api.common.presentation.DataFixture.Users.TEST_USER_2
import com.devfloor.hongit.api.common.presentation.DataFixture.Users.TEST_USER_3
import com.devfloor.hongit.api.common.presentation.DataInitializeController.Companion.INIT_API_URI
import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.article.domain.ArticleRepository
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavorite
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteRepository
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.hongit.core.articlehashtag.domain.ArticleHashtag
import com.devfloor.hongit.core.articlehashtag.domain.ArticleHashtagRepository
import com.devfloor.hongit.core.articleoption.domain.ArticleOption
import com.devfloor.hongit.core.articleoption.domain.ArticleOptionRepository
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.board.domain.BoardRepository
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.boardoption.domain.BoardOption
import com.devfloor.hongit.core.boardoption.domain.BoardOptionRepository
import com.devfloor.hongit.core.comment.domain.Comment
import com.devfloor.hongit.core.comment.domain.CommentRepository
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import com.devfloor.hongit.core.course.domain.Course
import com.devfloor.hongit.core.course.domain.CourseRepository
import com.devfloor.hongit.core.course.domain.Grade
import com.devfloor.hongit.core.course.domain.OpeningSemester
import com.devfloor.hongit.core.course.domain.Schedule
import com.devfloor.hongit.core.course.domain.Semester
import com.devfloor.hongit.core.course.domain.Timetable
import com.devfloor.hongit.core.hashtag.domain.Hashtag
import com.devfloor.hongit.core.hashtag.domain.HashtagRepository
import com.devfloor.hongit.core.option.domain.Option
import com.devfloor.hongit.core.option.domain.OptionRepository
import com.devfloor.hongit.core.option.domain.OptionType
import com.devfloor.hongit.core.professor.domain.Professor
import com.devfloor.hongit.core.professor.domain.ProfessorRepository
import com.devfloor.hongit.core.subject.domain.Subject
import com.devfloor.hongit.core.subject.domain.SubjectRepository
import com.devfloor.hongit.core.user.domain.Email
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserRepository
import com.devfloor.hongit.core.user.domain.UserType
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.Year
import javax.persistence.EntityManager

@Slf4j
@Profile(value = ["local", "dev"])
@RestController
@Transactional
@RequestMapping(value = [INIT_API_URI])
class DataInitializeController(
    private val entityManager: EntityManager,

    private val userRepository: UserRepository,
    private val professorRepository: ProfessorRepository,
    private val subjectRepository: SubjectRepository,
    private val optionRepository: OptionRepository,
    private val hashtagRepository: HashtagRepository,
    private val courseRepository: CourseRepository,
    private val boardRepository: BoardRepository,
    private val boardOptionRepository: BoardOptionRepository,
    private val articleRepository: ArticleRepository,
    private val articleHashtagRepository: ArticleHashtagRepository,
    private val articleOptionRepository: ArticleOptionRepository,
    private val commentRepository: CommentRepository,
    private val articleFavoriteRepository: ArticleFavoriteRepository,
) {
    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun removeAll() {
        deleteAll().also { log.info("[DataInitializeController] 데이터 삭제 완료") }
        resetAllAutoIncrement().also { log.info("[DataInitializeController] auto_increment 초기화 완료") }
        saveAll().also { log.info("[DataInitializeController] 데이터 생성 완료") }
    }

    fun deleteAll() {
        articleFavoriteRepository.deleteAll()
        commentRepository.deleteAll()
        articleOptionRepository.deleteAll()
        articleHashtagRepository.deleteAll()
        articleRepository.deleteAll()
        courseRepository.deleteAll()
        boardOptionRepository.deleteAll()
        boardRepository.deleteAll()
        hashtagRepository.deleteAll()
        optionRepository.deleteAll()
        subjectRepository.deleteAll()
        professorRepository.deleteAll()
        userRepository.deleteAll()
    }

    fun resetAllAutoIncrement() {
        listOf(
            "article_favorites", "comments", "article_options", "article_hashtags", "articles", "courses",
            "board_options", "boards", "hashtags", "options", "subjects", "professors", "users"
        )
            .forEach { resetAutoIncrement(it) }
    }

    fun resetAutoIncrement(table: String) {
        entityManager.createNativeQuery("ALTER TABLE $table AUTO_INCREMENT = 1").executeUpdate()
    }

    fun saveAll() {
        userRepository.saveAll(TEST_USERS)
        professorRepository.saveAll(TEST_PROFESSORS)
        subjectRepository.saveAll(TEST_SUBJECTS)
        optionRepository.saveAll(TEST_OPTIONS)
        hashtagRepository.saveAll(TEST_HASHTAGS)

        val boards = boardRepository.saveAll(TEST_BOARDS)
        articleRepository.saveAll(TEST_ARTICLES + generateArticles(boards))

        boardOptionRepository.saveAll(TEST_BOARD_OPTIONS)
        courseRepository.saveAll(TEST_COURSES)
        articleHashtagRepository.saveAll(TEST_ARTICLE_HASHTAGS)
        articleOptionRepository.saveAll(TEST_ARTICLE_OPTIONS)
        commentRepository.saveAll(TEST_COMMENTS)
        articleFavoriteRepository.saveAll(TEST_ARTICLE_FAVORITES)
    }

    private fun generateArticles(boards: List<Board>): List<Article> = boards.flatMap {
        (0..10).map { idx ->
            Article(
                title = "${it.type.text} test 게시물 - $idx",
                anonymous = false,
                content = "test 게시물 $idx ".repeat(idx),
                author = TEST_USERS.random(),
                board = it
            )
        }
    }

    companion object {
        const val INIT_API_URI = "$BASE_API_URI/data/init"
    }
}

private object DataFixture {
    object Users {
        val TEST_USER_1 = User(
            username = "lxxjn0",
            password = "password1",
            nickname = "lxxjn0",
            email = Email.from("lxxjn0@mail.hongik.ac.kr"),
            type = UserType.STUDENT,
            image = "https://avatars.githubusercontent.com/u/48052622?v=4",
            classOf = "B411158",
            github = "lxxjn0",
            blog = "https://velog.io/@lxxjn0",
            description = "안녕하세요, 개발자 이XX입니다."
        )
        val TEST_USER_2 = User(
            username = "test",
            password = "password2",
            nickname = "test",
            email = Email.from("test@g.hongik.ac.kr"),
            type = UserType.STUDENT,
            classOf = "B000000",
            description = "안녕하세요, test 사용자입니다."
        )
        val TEST_USER_3 = User(
            username = "pjh",
            password = "password2",
            nickname = "pjh",
            email = Email.from("pjh@g.hongik.ac.kr"),
            type = UserType.STUDENT,
            classOf = "B411092",
            description = "안녕하세요, pjh 사용자입니다."
        )
        val TEST_USERS = listOf(TEST_USER_1, TEST_USER_2, TEST_USER_3)
    }

    object Professors {
        val TEST_PROFESSOR_1 = Professor("정균락", "krchong@hongik.ac.kr")
        val TEST_PROFESSOR_2 = Professor("하란")
        val TEST_PROFESSOR_3 = Professor("김선일", "ksi@hongik.ac.kr")
        val TEST_PROFESSORS = listOf(TEST_PROFESSOR_1, TEST_PROFESSOR_2, TEST_PROFESSOR_3)
    }

    object Subjects {
        val TEST_SUBJECT_1 = Subject("알고리즘")
        val TEST_SUBJECT_2 = Subject("확률과통계")
        val TEST_SUBJECT_3 = Subject("컴퓨터공학개론")
        val TEST_SUBJECT_4 = Subject("인공지능")
        val TEST_SUBJECT_5 = Subject("오토마타")
        val TEST_SUBJECT_6 = Subject("컴퓨터구조")
        val TEST_SUBJECTS =
            listOf(TEST_SUBJECT_1, TEST_SUBJECT_2, TEST_SUBJECT_3, TEST_SUBJECT_4, TEST_SUBJECT_5, TEST_SUBJECT_6)
    }

    object Options {
        val TEST_OPTION_1 = Option("1분반", OptionType.COURSE_GROUP)
        val TEST_OPTION_2 = Option("2분반", OptionType.COURSE_GROUP)
        val TEST_OPTION_3 = Option("3분반", OptionType.COURSE_GROUP)
        val TEST_OPTION_4 = Option("4분반", OptionType.COURSE_GROUP)
        val TEST_OPTION_5 = Option("5분반", OptionType.COURSE_GROUP)
        val TEST_OPTION_6 = Option("질문", OptionType.ARTICLE_KIND)
        val TEST_OPTION_7 = Option("일정", OptionType.ARTICLE_KIND)
        val TEST_OPTIONS = listOf(
            TEST_OPTION_1,
            TEST_OPTION_2,
            TEST_OPTION_3,
            TEST_OPTION_4,
            TEST_OPTION_5,
            TEST_OPTION_6,
            TEST_OPTION_7
        )
    }

    object Hashtags {
        val TEST_HASHTAG_1 = Hashtag("해시태그1")
        val TEST_HASHTAG_2 = Hashtag("해시태그2")
        val TEST_HASHTAG_3 = Hashtag("해시태그3")
        val TEST_HASHTAG_4 = Hashtag("해시태그4")
        val TEST_HASHTAG_5 = Hashtag("해시태그5")
        val TEST_HASHTAG_6 = Hashtag("해시태그6")
        val TEST_HASHTAGS =
            listOf(TEST_HASHTAG_1, TEST_HASHTAG_2, TEST_HASHTAG_3, TEST_HASHTAG_4, TEST_HASHTAG_5, TEST_HASHTAG_6)
    }

    object Courses {
        val TEST_OPENING_SEMESTER = OpeningSemester(Year.parse("2021"), Semester.SECOND_SEMESTER)

        val TEST_COURSE_1 = Course(
            code = "101503-1",
            openingSemester = TEST_OPENING_SEMESTER,
            professor = TEST_PROFESSOR_1,
            subject = TEST_SUBJECT_1,
            grade = Grade.JUNIOR,
            option = TEST_OPTION_1,
            timetable = Timetable(listOf(Schedule.from("월6"), Schedule.from("화6"), Schedule.from("수6")))
        ).apply { updateBoard(TEST_BOARD_1) }
        val TEST_COURSE_2 = Course(
            code = "101503-2",
            openingSemester = TEST_OPENING_SEMESTER,
            professor = TEST_PROFESSOR_1,
            subject = TEST_SUBJECT_1,
            grade = Grade.JUNIOR,
            option = TEST_OPTION_2,
            timetable = Timetable(listOf(Schedule.from("월7"), Schedule.from("화7"), Schedule.from("수7")))
        ).apply { updateBoard(TEST_BOARD_1) }
        val TEST_COURSE_3 = Course(
            code = "101503-3",
            openingSemester = TEST_OPENING_SEMESTER,
            professor = TEST_PROFESSOR_1,
            subject = TEST_SUBJECT_1,
            grade = Grade.JUNIOR,
            option = TEST_OPTION_3,
            timetable = Timetable(listOf(Schedule.from("월9"), Schedule.from("화9"), Schedule.from("수9")))
        ).apply { updateBoard(TEST_BOARD_1) }
        val TEST_COURSE_4 = Course(
            code = "101503-4",
            openingSemester = TEST_OPENING_SEMESTER,
            professor = TEST_PROFESSOR_2,
            subject = TEST_SUBJECT_1,
            grade = Grade.JUNIOR,
            option = TEST_OPTION_4,
            timetable = Timetable(listOf(Schedule.from("월2"), Schedule.from("화2"), Schedule.from("수2")))
        ).apply { updateBoard(TEST_BOARD_2) }
        val TEST_COURSE_5 = Course(
            code = "101503-5",
            openingSemester = TEST_OPENING_SEMESTER,
            professor = TEST_PROFESSOR_2,
            subject = TEST_SUBJECT_1,
            grade = Grade.JUNIOR,
            option = TEST_OPTION_5,
            timetable = Timetable(listOf(Schedule.from("월3"), Schedule.from("화3"), Schedule.from("수3")))
        ).apply { updateBoard(TEST_BOARD_2) }
        val TEST_COURSES = listOf(TEST_COURSE_1, TEST_COURSE_2, TEST_COURSE_3, TEST_COURSE_4, TEST_COURSE_5)
    }

    object Boards {
        val TEST_BOARD_1 = Board(
            title = "${TEST_SUBJECT_1.name} - ${TEST_PROFESSOR_1.name}",
            type = BoardType.COURSE_BOARD
        )
        val TEST_BOARD_2 = Board(
            title = "${TEST_SUBJECT_1.name} - ${TEST_PROFESSOR_2.name}",
            type = BoardType.COURSE_BOARD
        )
        val TEST_BOARD_3 = Board(title = "QnA", type = BoardType.QNA_BOARD)
        val TEST_BOARD_4 = Board(title = "Community", type = BoardType.COMMUNITY_BOARD)
        val TEST_BOARD_5 = Board(title = "Gathering", type = BoardType.GATHERING_BOARD)
        val TEST_BOARD_6 = Board(title = "Recruit", type = BoardType.RECRUIT_BOARD)
        val TEST_BOARDS = listOf(
            TEST_BOARD_1,
            TEST_BOARD_2,
            TEST_BOARD_3,
            TEST_BOARD_4,
            TEST_BOARD_5,
            TEST_BOARD_6,
        )
    }

    object BoardOptions {
        val TEST_BOARD_OPTION_1 = BoardOption(TEST_BOARD_1, TEST_OPTION_6)
        val TEST_BOARD_OPTION_2 = BoardOption(TEST_BOARD_1, TEST_OPTION_7)
        val TEST_BOARD_OPTION_3 = BoardOption(TEST_BOARD_2, TEST_OPTION_6)
        val TEST_BOARD_OPTION_4 = BoardOption(TEST_BOARD_2, TEST_OPTION_7)
        val TEST_BOARD_OPTIONS =
            listOf(TEST_BOARD_OPTION_1, TEST_BOARD_OPTION_2, TEST_BOARD_OPTION_3, TEST_BOARD_OPTION_4)
    }

    object Articles {
        val TEST_ARTICLE_1 = Article(
            title = "테스트 게시글 1 입니다.",
            anonymous = false,
            content = """
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                      labore et dolore magna aliqua. Arcu odio ut sem nulla pharetra diam sit. Enim sit amet venenatis
                      urna cursus eget nunc scelerisque viverra. Sit amet mauris commodo quis imperdiet massa tincidunt.
                      Ultricies integer quis auctor elit sed vulputate mi. In metus vulputate eu scelerisque felis.
                      Aliquam eleifend mi in nulla posuere sollicitudin aliquam ultrices. Nisl condimentum id venenatis
                      a condimentum vitae sapien pellentesque habitant. Mauris sit amet massa vitae tortor. Sit amet
                      consectetur adipiscing elit. Purus sit amet volutpat consequat. Sapien faucibus et molestie ac
                      feugiat sed lectus vestibulum. Fermentum posuere urna nec tincidunt praesent semper. Quisque
                      egestas diam in arcu cursus euismod quis viverra. Nisl condimentum id venenatis a. Volutpat
                      consequat mauris nunc congue. Risus nullam eget felis eget nunc lobortis. Metus dictum at tempor
                      commodo ullamcorper a lacus vestibulum sed. Ultrices mi tempus imperdiet nulla malesuada.
                      Etiam sit amet nisl purus in mollis nunc. Turpis tincidunt id aliquet risus feugiat in ante
                      metus. Dui sapien eget mi proin sed libero enim. Nulla pellentesque dignissim enim sit amet.
                      Quis enim lobortis scelerisque fermentum dui. Laoreet id donec ultrices tincidunt arcu non
                      sodales. Quam vulputate dignissim suspendisse in est ante in. Sodales ut etiam sit amet nisl.
                      Est ante in nibh mauris cursus mattis. Senectus et netus et malesuada fames ac turpis egestas.
            """.trimIndent(),
            author = TEST_USER_1,
            board = TEST_BOARD_1
        )
        val TEST_ARTICLE_2 = Article(
            title = "테스트 게시글 2 입니다.",
            anonymous = false,
            content = """
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                      labore et dolore magna aliqua. Arcu odio ut sem nulla pharetra diam sit. Enim sit amet venenatis
                      urna cursus eget nunc scelerisque viverra. Sit amet mauris commodo quis imperdiet massa tincidunt.
                      Ultricies integer quis auctor elit sed vulputate mi. In metus vulputate eu scelerisque felis.
                      Aliquam eleifend mi in nulla posuere sollicitudin aliquam ultrices. Nisl condimentum id venenatis
                      a condimentum vitae sapien pellentesque habitant. Mauris sit amet massa vitae tortor. Sit amet
                      consectetur adipiscing elit. Purus sit amet volutpat consequat. Sapien faucibus et molestie ac
                      feugiat sed lectus vestibulum. Fermentum posuere urna nec tincidunt praesent semper. Quisque
                      egestas diam in arcu cursus euismod quis viverra. Nisl condimentum id venenatis a. Volutpat
                      consequat mauris nunc congue. Risus nullam eget felis eget nunc lobortis. Metus dictum at tempor
                      commodo ullamcorper a lacus vestibulum sed. Ultrices mi tempus imperdiet nulla malesuada.
            """.trimIndent(),
            author = TEST_USER_2,
            board = TEST_BOARD_2
        )
        val TEST_ARTICLE_3 = Article(
            title = "테스트 게시글 3 입니다.",
            anonymous = true,
            content = """
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                      labore et dolore magna aliqua. Arcu odio ut sem nulla pharetra diam sit. Enim sit amet venenatis
                      urna cursus eget nunc scelerisque viverra. Sit amet mauris commodo quis imperdiet massa tincidunt.
                      Ultricies integer quis auctor elit sed vulputate mi. In metus vulputate eu scelerisque felis.
                      Aliquam eleifend mi in nulla posuere sollicitudin aliquam ultrices. Nisl condimentum id venenatis
                      a condimentum vitae sapien pellentesque habitant. Mauris sit amet massa vitae tortor. Sit amet
                      consectetur adipiscing elit. Purus sit amet volutpat consequat. Sapien faucibus et molestie ac
                      feugiat sed lectus vestibulum. Fermentum posuere urna nec tincidunt praesent semper. Quisque
                      egestas diam in arcu cursus euismod quis viverra. Nisl condimentum id venenatis a. Volutpat
                      consequat mauris nunc congue. Risus nullam eget felis eget nunc lobortis. Metus dictum at tempor
                      commodo ullamcorper a lacus vestibulum sed. Ultrices mi tempus imperdiet nulla malesuada.
            """.trimIndent(),
            author = TEST_USER_1,
            board = TEST_BOARD_2
        )
        val TEST_ARTICLE_4 = Article(
            title = "테스트 게시글 4 입니다.",
            anonymous = false,
            content = """
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                      labore et dolore magna aliqua. Arcu odio ut sem nulla pharetra diam sit. Enim sit amet venenatis
                      urna cursus eget nunc scelerisque viverra. Sit amet mauris commodo quis imperdiet massa tincidunt.
                      Ultricies integer quis auctor elit sed vulputate mi. In metus vulputate eu scelerisque felis.
                      Aliquam eleifend mi in nulla posuere sollicitudin aliquam ultrices. Nisl condimentum id venenatis
                      a condimentum vitae sapien pellentesque habitant. Mauris sit amet massa vitae tortor. Sit amet
                      consectetur adipiscing elit. Purus sit amet volutpat consequat. Sapien faucibus et molestie ac
                      feugiat sed lectus vestibulum. Fermentum posuere urna nec tincidunt praesent semper. Quisque
                      egestas diam in arcu cursus euismod quis viverra. Nisl condimentum id venenatis a. Volutpat
                      consequat mauris nunc congue. Risus nullam eget felis eget nunc lobortis. Metus dictum at tempor
                      commodo ullamcorper a lacus vestibulum sed. Ultrices mi tempus imperdiet nulla malesuada.
            """.trimIndent(),
            author = TEST_USER_3,
            board = TEST_BOARD_3
        )
        val TEST_ARTICLE_5 = Article(
            title = "테스트 게시글 5 입니다.",
            anonymous = false,
            content = """
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                      labore et dolore magna aliqua. Arcu odio ut sem nulla pharetra diam sit. Enim sit amet venenatis
                      urna cursus eget nunc scelerisque viverra. Sit amet mauris commodo quis imperdiet massa tincidunt.
                      Ultricies integer quis auctor elit sed vulputate mi. In metus vulputate eu scelerisque felis.
                      Aliquam eleifend mi in nulla posuere sollicitudin aliquam ultrices. Nisl condimentum id venenatis
                      a condimentum vitae sapien pellentesque habitant. Mauris sit amet massa vitae tortor. Sit amet
                      consectetur adipiscing elit. Purus sit amet volutpat consequat. Sapien faucibus et molestie ac
                      feugiat sed lectus vestibulum. Fermentum posuere urna nec tincidunt praesent semper. Quisque
                      egestas diam in arcu cursus euismod quis viverra. Nisl condimentum id venenatis a. Volutpat
                      consequat mauris nunc congue. Risus nullam eget felis eget nunc lobortis. Metus dictum at tempor
                      commodo ullamcorper a lacus vestibulum sed. Ultrices mi tempus imperdiet nulla malesuada.
            """.trimIndent(),
            author = TEST_USER_3,
            board = TEST_BOARD_3
        )
        val TEST_ARTICLE_6 = Article(
            title = "테스트 게시글 6 입니다.",
            anonymous = false,
            content = """
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                      labore et dolore magna aliqua. Arcu odio ut sem nulla pharetra diam sit. Enim sit amet venenatis
                      urna cursus eget nunc scelerisque viverra. Sit amet mauris commodo quis imperdiet massa tincidunt.
                      Ultricies integer quis auctor elit sed vulputate mi. In metus vulputate eu scelerisque felis.
                      Aliquam eleifend mi in nulla posuere sollicitudin aliquam ultrices. Nisl condimentum id venenatis
                      a condimentum vitae sapien pellentesque habitant. Mauris sit amet massa vitae tortor. Sit amet
                      consectetur adipiscing elit. Purus sit amet volutpat consequat. Sapien faucibus et molestie ac
                      feugiat sed lectus vestibulum. Fermentum posuere urna nec tincidunt praesent semper. Quisque
                      egestas diam in arcu cursus euismod quis viverra. Nisl condimentum id venenatis a. Volutpat
                      consequat mauris nunc congue. Risus nullam eget felis eget nunc lobortis. Metus dictum at tempor
                      commodo ullamcorper a lacus vestibulum sed. Ultrices mi tempus imperdiet nulla malesuada.
            """.trimIndent(),
            author = TEST_USER_3,
            board = TEST_BOARD_4
        )
        val TEST_ARTICLE_7 = Article(
            title = "테스트 게시글 7 입니다.",
            anonymous = false,
            content = """
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                      labore et dolore magna aliqua. Arcu odio ut sem nulla pharetra diam sit. Enim sit amet venenatis
                      urna cursus eget nunc scelerisque viverra. Sit amet mauris commodo quis imperdiet massa tincidunt.
                      Ultricies integer quis auctor elit sed vulputate mi. In metus vulputate eu scelerisque felis.
                      Aliquam eleifend mi in nulla posuere sollicitudin aliquam ultrices. Nisl condimentum id venenatis
                      a condimentum vitae sapien pellentesque habitant. Mauris sit amet massa vitae tortor. Sit amet
                      consectetur adipiscing elit. Purus sit amet volutpat consequat. Sapien faucibus et molestie ac
                      feugiat sed lectus vestibulum. Fermentum posuere urna nec tincidunt praesent semper. Quisque
                      egestas diam in arcu cursus euismod quis viverra. Nisl condimentum id venenatis a. Volutpat
                      consequat mauris nunc congue. Risus nullam eget felis eget nunc lobortis. Metus dictum at tempor
                      commodo ullamcorper a lacus vestibulum sed. Ultrices mi tempus imperdiet nulla malesuada.
            """.trimIndent(),
            author = TEST_USER_1,
            board = TEST_BOARD_5
        )
        val TEST_ARTICLE_8 = Article(
            title = "테스트 게시글 8 입니다.",
            anonymous = false,
            content = """
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                      labore et dolore magna aliqua. Arcu odio ut sem nulla pharetra diam sit. Enim sit amet venenatis
                      urna cursus eget nunc scelerisque viverra. Sit amet mauris commodo quis imperdiet massa tincidunt.
                      Ultricies integer quis auctor elit sed vulputate mi. In metus vulputate eu scelerisque felis.
                      Aliquam eleifend mi in nulla posuere sollicitudin aliquam ultrices. Nisl condimentum id venenatis
                      a condimentum vitae sapien pellentesque habitant. Mauris sit amet massa vitae tortor. Sit amet
                      consectetur adipiscing elit. Purus sit amet volutpat consequat. Sapien faucibus et molestie ac
                      feugiat sed lectus vestibulum. Fermentum posuere urna nec tincidunt praesent semper. Quisque
                      egestas diam in arcu cursus euismod quis viverra. Nisl condimentum id venenatis a. Volutpat
                      consequat mauris nunc congue. Risus nullam eget felis eget nunc lobortis. Metus dictum at tempor
                      commodo ullamcorper a lacus vestibulum sed. Ultrices mi tempus imperdiet nulla malesuada.
            """.trimIndent(),
            author = TEST_USER_1,
            board = TEST_BOARD_6
        )
        val TEST_ARTICLES = listOf(
            TEST_ARTICLE_1,
            TEST_ARTICLE_2,
            TEST_ARTICLE_3,
            TEST_ARTICLE_4,
            TEST_ARTICLE_5,
            TEST_ARTICLE_6,
            TEST_ARTICLE_7,
            TEST_ARTICLE_8
        )
    }

    object ArticleHashtags {
        val TEST_ARTICLE_HASHTAG_1 = ArticleHashtag(TEST_ARTICLE_1, TEST_HASHTAG_1)
        val TEST_ARTICLE_HASHTAG_2 = ArticleHashtag(TEST_ARTICLE_1, TEST_HASHTAG_3)
        val TEST_ARTICLE_HASHTAG_3 = ArticleHashtag(TEST_ARTICLE_1, TEST_HASHTAG_5)
        val TEST_ARTICLE_HASHTAG_4 = ArticleHashtag(TEST_ARTICLE_2, TEST_HASHTAG_1)
        val TEST_ARTICLE_HASHTAG_5 = ArticleHashtag(TEST_ARTICLE_2, TEST_HASHTAG_2)
        val TEST_ARTICLE_HASHTAG_6 = ArticleHashtag(TEST_ARTICLE_3, TEST_HASHTAG_4)
        val TEST_ARTICLE_HASHTAG_7 = ArticleHashtag(TEST_ARTICLE_4, TEST_HASHTAG_2)
        val TEST_ARTICLE_HASHTAG_8 = ArticleHashtag(TEST_ARTICLE_4, TEST_HASHTAG_5)
        val TEST_ARTICLE_HASHTAG_9 = ArticleHashtag(TEST_ARTICLE_4, TEST_HASHTAG_6)
        val TEST_ARTICLE_HASHTAGS = listOf(
            TEST_ARTICLE_HASHTAG_1,
            TEST_ARTICLE_HASHTAG_2,
            TEST_ARTICLE_HASHTAG_3,
            TEST_ARTICLE_HASHTAG_4,
            TEST_ARTICLE_HASHTAG_5,
            TEST_ARTICLE_HASHTAG_6,
            TEST_ARTICLE_HASHTAG_7,
            TEST_ARTICLE_HASHTAG_8,
            TEST_ARTICLE_HASHTAG_9
        )
    }

    object ArticleOptions {
        val TEST_ARTICLE_OPTION_1 = ArticleOption(TEST_ARTICLE_1, TEST_OPTION_1)
        val TEST_ARTICLE_OPTION_2 = ArticleOption(TEST_ARTICLE_1, TEST_OPTION_2)
        val TEST_ARTICLE_OPTION_3 = ArticleOption(TEST_ARTICLE_1, TEST_OPTION_6)
        val TEST_ARTICLE_OPTION_4 = ArticleOption(TEST_ARTICLE_2, TEST_OPTION_4)
        val TEST_ARTICLE_OPTION_5 = ArticleOption(TEST_ARTICLE_2, TEST_OPTION_7)
        val TEST_ARTICLE_OPTION_6 = ArticleOption(TEST_ARTICLE_3, TEST_OPTION_4)
        val TEST_ARTICLE_OPTION_7 = ArticleOption(TEST_ARTICLE_3, TEST_OPTION_5)
        val TEST_ARTICLE_OPTION_8 = ArticleOption(TEST_ARTICLE_3, TEST_OPTION_6)
        val TEST_ARTICLE_OPTION_9 = ArticleOption(TEST_ARTICLE_4, TEST_OPTION_1)
        val TEST_ARTICLE_OPTION_10 = ArticleOption(TEST_ARTICLE_4, TEST_OPTION_6)
        val TEST_ARTICLE_OPTIONS = listOf(
            TEST_ARTICLE_OPTION_1,
            TEST_ARTICLE_OPTION_2,
            TEST_ARTICLE_OPTION_3,
            TEST_ARTICLE_OPTION_4,
            TEST_ARTICLE_OPTION_5,
            TEST_ARTICLE_OPTION_6,
            TEST_ARTICLE_OPTION_7,
            TEST_ARTICLE_OPTION_8,
            TEST_ARTICLE_OPTION_9,
            TEST_ARTICLE_OPTION_10
        )
    }

    object Comments {
        val TEST_COMMENT_1 = Comment(
            article = TEST_ARTICLE_1,
            author = TEST_USER_1,
            anonymous = true,
            content = "테스트 댓글 1 입니다.",
        )
        val TEST_COMMENT_2 = Comment(
            article = TEST_ARTICLE_1,
            author = TEST_USER_1,
            anonymous = false,
            content = "테스트 댓글 2 입니다.",
        )
        val TEST_COMMENT_3 = Comment(
            article = TEST_ARTICLE_1,
            author = TEST_USER_2,
            anonymous = false,
            content = "테스트 댓글 3 입니다.",
        )
        val TEST_COMMENT_4 = Comment(
            article = TEST_ARTICLE_2,
            author = TEST_USER_1,
            anonymous = false,
            content = "테스트 댓글 4 입니다.",
        )
        val TEST_COMMENT_5 = Comment(
            article = TEST_ARTICLE_3,
            author = TEST_USER_2,
            anonymous = true,
            content = "테스트 댓글 5 입니다.",
        )
        val TEST_COMMENTS = listOf(TEST_COMMENT_1, TEST_COMMENT_2, TEST_COMMENT_3, TEST_COMMENT_4, TEST_COMMENT_5)
    }

    object ArticleFavorites {
        val TEST_ARTICLE_FAVORITE_1 = ArticleFavorite(
            article = TEST_ARTICLE_1,
            user = TEST_USER_1,
            type = ArticleFavoriteType.FAVORITE
        )
        val TEST_ARTICLE_FAVORITE_2 = ArticleFavorite(
            article = TEST_ARTICLE_1,
            user = TEST_USER_2,
            type = ArticleFavoriteType.FAVORITE
        )
        val TEST_ARTICLE_FAVORITE_3 = ArticleFavorite(
            article = TEST_ARTICLE_1,
            user = TEST_USER_3,
            type = ArticleFavoriteType.FAVORITE
        )
        val TEST_ARTICLE_FAVORITE_4 = ArticleFavorite(
            article = TEST_ARTICLE_2,
            user = TEST_USER_1,
            type = ArticleFavoriteType.FAVORITE
        )
        val TEST_ARTICLE_FAVORITE_5 = ArticleFavorite(
            article = TEST_ARTICLE_2,
            user = TEST_USER_2,
            type = ArticleFavoriteType.FAVORITE
        )
        val TEST_ARTICLE_FAVORITE_6 = ArticleFavorite(
            article = TEST_ARTICLE_3,
            user = TEST_USER_1,
            type = ArticleFavoriteType.FAVORITE
        )
        val TEST_ARTICLE_FAVORITES = listOf(
            TEST_ARTICLE_FAVORITE_1,
            TEST_ARTICLE_FAVORITE_2,
            TEST_ARTICLE_FAVORITE_3,
            TEST_ARTICLE_FAVORITE_4,
            TEST_ARTICLE_FAVORITE_5,
            TEST_ARTICLE_FAVORITE_6
        )
    }
}
