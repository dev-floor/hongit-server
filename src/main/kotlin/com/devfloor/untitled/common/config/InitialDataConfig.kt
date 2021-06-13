package com.devfloor.untitled.common.config

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.article.domain.ArticleRepository
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtagRepository
import com.devfloor.untitled.articleoption.domain.ArticleOption
import com.devfloor.untitled.articleoption.domain.ArticleOptionRepository
import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.board.domain.BoardRepository
import com.devfloor.untitled.board.domain.BoardType
import com.devfloor.untitled.boardcourse.domain.BoardCourse
import com.devfloor.untitled.boardcourse.domain.BoardCourseRepository
import com.devfloor.untitled.boardoption.domain.BoardOption
import com.devfloor.untitled.boardoption.domain.BoardOptionRepository
import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.comment.domain.CommentRepository
import com.devfloor.untitled.common.domain.Grade
import com.devfloor.untitled.common.domain.OpeningSemester
import com.devfloor.untitled.common.domain.Semester
import com.devfloor.untitled.course.domain.Course
import com.devfloor.untitled.course.domain.CourseRepository
import com.devfloor.untitled.course.domain.Schedule
import com.devfloor.untitled.course.domain.Timetable
import com.devfloor.untitled.hashtag.domain.Hashtag
import com.devfloor.untitled.hashtag.domain.HashtagRepository
import com.devfloor.untitled.option.domain.Option
import com.devfloor.untitled.option.domain.OptionRepository
import com.devfloor.untitled.option.domain.OptionType
import com.devfloor.untitled.professor.domain.Professor
import com.devfloor.untitled.professor.domain.ProfessorRepository
import com.devfloor.untitled.subject.domain.Subject
import com.devfloor.untitled.subject.domain.SubjectRepository
import com.devfloor.untitled.user.domain.User
import com.devfloor.untitled.user.domain.UserRepository
import com.devfloor.untitled.user.domain.UserType
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.Year

@Slf4j
@Profile(value = ["local", "dev"])
@Component
class InitialDataConfig(
    private val userRepository: UserRepository,
    private val professorRepository: ProfessorRepository,
    private val subjectRepository: SubjectRepository,
    private val optionRepository: OptionRepository,
    private val hashtagRepository: HashtagRepository,
    private val courseRepository: CourseRepository,
    private val boardRepository: BoardRepository,
    private val boardCourseRepository: BoardCourseRepository,
    private val boardOptionRepository: BoardOptionRepository,
    private val articleRepository: ArticleRepository,
    private val articleHashtagRepository: ArticleHashtagRepository,
    private val articleOptionRepository: ArticleOptionRepository,
    private val commentRepository: CommentRepository,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        userRepository.saveAll(listOf(TEST_USER_1, TEST_USER_2))
        professorRepository.saveAll(listOf(TEST_PROFESSOR_1, TEST_PROFESSOR_2))
        subjectRepository.save(TEST_SUBJECT_1)
        optionRepository.saveAll(
            listOf(
                TEST_OPTION_1, TEST_OPTION_2, TEST_OPTION_3, TEST_OPTION_4, TEST_OPTION_5,
                TEST_OPTION_6, TEST_OPTION_7
            )
        )
        hashtagRepository.saveAll(
            listOf(
                TEST_HASHTAG_1, TEST_HASHTAG_2, TEST_HASHTAG_3, TEST_HASHTAG_4, TEST_HASHTAG_5,
                TEST_HASHTAG_6
            )
        )
        courseRepository.saveAll(listOf(TEST_COURSE_1, TEST_COURSE_2, TEST_COURSE_3, TEST_COURSE_4, TEST_COURSE_5))
        boardRepository.saveAll(listOf(TEST_BOARD_1, TEST_BOARD_2))
        boardCourseRepository.saveAll(
            listOf(
                TEST_BOARD_COURSE_1, TEST_BOARD_COURSE_2, TEST_BOARD_COURSE_3,
                TEST_BOARD_COURSE_4, TEST_BOARD_COURSE_5
            )
        )
        boardOptionRepository.saveAll(
            listOf(
                TEST_BOARD_OPTION_1, TEST_BOARD_OPTION_2, TEST_BOARD_OPTION_3,
                TEST_BOARD_OPTION_4
            )
        )
        articleRepository.saveAll(listOf(TEST_ARTICLE_1, TEST_ARTICLE_2, TEST_ARTICLE_3, TEST_ARTICLE_4))
        articleHashtagRepository.saveAll(
            listOf(
                TEST_ARTICLE_HASHTAG_1, TEST_ARTICLE_HASHTAG_2, TEST_ARTICLE_HASHTAG_3,
                TEST_ARTICLE_HASHTAG_4, TEST_ARTICLE_HASHTAG_5, TEST_ARTICLE_HASHTAG_6, TEST_ARTICLE_HASHTAG_7,
                TEST_ARTICLE_HASHTAG_8, TEST_ARTICLE_HASHTAG_9
            )
        )
        articleOptionRepository.saveAll(
            listOf(
                TEST_ARTICLE_OPTION_1, TEST_ARTICLE_OPTION_2, TEST_ARTICLE_OPTION_3,
                TEST_ARTICLE_OPTION_4, TEST_ARTICLE_OPTION_5, TEST_ARTICLE_OPTION_6, TEST_ARTICLE_OPTION_7,
                TEST_ARTICLE_OPTION_8, TEST_ARTICLE_OPTION_9, TEST_ARTICLE_OPTION_10
            )
        )
        commentRepository.saveAll(
            listOf(
                TEST_COMMENT_1, TEST_COMMENT_2, TEST_COMMENT_3, TEST_COMMENT_4,
                TEST_COMMENT_5
            )
        )
    }

    companion object {
        private val TEST_USER_1 = User(
            nickname = "lxxjn0",
            type = UserType.STUDENT,
            image = "https://avatars.githubusercontent.com/u/48052622?v=4",
            classOf = "B411158",
            github = "lxxjn0",
            blog = "https://velog.io/@lxxjn0",
            description = "안녕하세요, 개발자 이XX입니다."
        )
        private val TEST_USER_2 = User(
            nickname = "test",
            type = UserType.STUDENT,
            image = null,
            classOf = "B000000",
            github = null,
            blog = null,
            description = "안녕하세요, test 사용자입니다."
        )

        private val TEST_PROFESSOR_1 = Professor("정균락", "krchong@hongik.ac.kr")
        private val TEST_PROFESSOR_2 = Professor("하란")

        private val TEST_SUBJECT_1 = Subject("알고리즘")

        private val TEST_OPTION_1 = Option("1분반", OptionType.COURSE_GROUP)
        private val TEST_OPTION_2 = Option("2분반", OptionType.COURSE_GROUP)
        private val TEST_OPTION_3 = Option("3분반", OptionType.COURSE_GROUP)
        private val TEST_OPTION_4 = Option("4분반", OptionType.COURSE_GROUP)
        private val TEST_OPTION_5 = Option("5분반", OptionType.COURSE_GROUP)
        private val TEST_OPTION_6 = Option("질문", OptionType.ARTICLE_KIND)
        private val TEST_OPTION_7 = Option("일정", OptionType.ARTICLE_KIND)

        private val TEST_HASHTAG_1 = Hashtag("해시태그1")
        private val TEST_HASHTAG_2 = Hashtag("해시태그2")
        private val TEST_HASHTAG_3 = Hashtag("해시태그3")
        private val TEST_HASHTAG_4 = Hashtag("해시태그4")
        private val TEST_HASHTAG_5 = Hashtag("해시태그5")
        private val TEST_HASHTAG_6 = Hashtag("해시태그6")

        private val TEST_OPENING_SEMESTER = OpeningSemester(Year.parse("2021"), Semester.SECOND_SEMESTER)

        private val TEST_COURSE_1 = Course(
            code = "101503-1",
            openingSemester = TEST_OPENING_SEMESTER,
            professor = TEST_PROFESSOR_1,
            subject = TEST_SUBJECT_1,
            grade = Grade.JUNIOR,
            option = TEST_OPTION_1,
            timetable = Timetable(listOf(Schedule.from("월6"), Schedule.from("화6"), Schedule.from("수6")))
        )
        private val TEST_COURSE_2 = Course(
            code = "101503-2",
            openingSemester = TEST_OPENING_SEMESTER,
            professor = TEST_PROFESSOR_1,
            subject = TEST_SUBJECT_1,
            grade = Grade.JUNIOR,
            option = TEST_OPTION_2,
            timetable = Timetable(listOf(Schedule.from("월7"), Schedule.from("화7"), Schedule.from("수7")))
        )
        private val TEST_COURSE_3 = Course(
            code = "101503-3",
            openingSemester = TEST_OPENING_SEMESTER,
            professor = TEST_PROFESSOR_1,
            subject = TEST_SUBJECT_1,
            grade = Grade.JUNIOR,
            option = TEST_OPTION_3,
            timetable = Timetable(listOf(Schedule.from("월9"), Schedule.from("화9"), Schedule.from("수9")))
        )
        private val TEST_COURSE_4 = Course(
            code = "101503-4",
            openingSemester = TEST_OPENING_SEMESTER,
            professor = TEST_PROFESSOR_2,
            subject = TEST_SUBJECT_1,
            grade = Grade.JUNIOR,
            option = TEST_OPTION_4,
            timetable = Timetable(listOf(Schedule.from("월2"), Schedule.from("화2"), Schedule.from("수2")))
        )
        private val TEST_COURSE_5 = Course(
            code = "101503-5",
            openingSemester = TEST_OPENING_SEMESTER,
            professor = TEST_PROFESSOR_2,
            subject = TEST_SUBJECT_1,
            grade = Grade.JUNIOR,
            option = TEST_OPTION_5,
            timetable = Timetable(listOf(Schedule.from("월3"), Schedule.from("화3"), Schedule.from("수3")))
        )

        private val TEST_BOARD_1 = Board(
            professor = TEST_PROFESSOR_1,
            subject = TEST_SUBJECT_1,
            openingSemester = TEST_OPENING_SEMESTER,
            BoardType.COURSE_BOARD
        )
        private val TEST_BOARD_2 = Board(
            professor = TEST_PROFESSOR_2,
            subject = TEST_SUBJECT_1,
            openingSemester = TEST_OPENING_SEMESTER,
            type = BoardType.COURSE_BOARD
        )

        private val TEST_BOARD_COURSE_1 = BoardCourse(TEST_BOARD_1, TEST_COURSE_1)
        private val TEST_BOARD_COURSE_2 = BoardCourse(TEST_BOARD_1, TEST_COURSE_2)
        private val TEST_BOARD_COURSE_3 = BoardCourse(TEST_BOARD_1, TEST_COURSE_3)
        private val TEST_BOARD_COURSE_4 = BoardCourse(TEST_BOARD_2, TEST_COURSE_4)
        private val TEST_BOARD_COURSE_5 = BoardCourse(TEST_BOARD_2, TEST_COURSE_5)

        private val TEST_BOARD_OPTION_1 = BoardOption(TEST_BOARD_1, TEST_OPTION_6)
        private val TEST_BOARD_OPTION_2 = BoardOption(TEST_BOARD_1, TEST_OPTION_7)
        private val TEST_BOARD_OPTION_3 = BoardOption(TEST_BOARD_2, TEST_OPTION_6)
        private val TEST_BOARD_OPTION_4 = BoardOption(TEST_BOARD_2, TEST_OPTION_7)

        private val TEST_ARTICLE_1 = Article(
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
        private val TEST_ARTICLE_2 = Article(
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
        private val TEST_ARTICLE_3 = Article(
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
        private val TEST_ARTICLE_4 = Article(
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
            author = TEST_USER_1,
            board = TEST_BOARD_1
        )

        private val TEST_ARTICLE_HASHTAG_1 = ArticleHashtag(TEST_ARTICLE_1, TEST_HASHTAG_1)
        private val TEST_ARTICLE_HASHTAG_2 = ArticleHashtag(TEST_ARTICLE_1, TEST_HASHTAG_3)
        private val TEST_ARTICLE_HASHTAG_3 = ArticleHashtag(TEST_ARTICLE_1, TEST_HASHTAG_5)
        private val TEST_ARTICLE_HASHTAG_4 = ArticleHashtag(TEST_ARTICLE_2, TEST_HASHTAG_1)
        private val TEST_ARTICLE_HASHTAG_5 = ArticleHashtag(TEST_ARTICLE_2, TEST_HASHTAG_2)
        private val TEST_ARTICLE_HASHTAG_6 = ArticleHashtag(TEST_ARTICLE_3, TEST_HASHTAG_4)
        private val TEST_ARTICLE_HASHTAG_7 = ArticleHashtag(TEST_ARTICLE_4, TEST_HASHTAG_2)
        private val TEST_ARTICLE_HASHTAG_8 = ArticleHashtag(TEST_ARTICLE_4, TEST_HASHTAG_5)
        private val TEST_ARTICLE_HASHTAG_9 = ArticleHashtag(TEST_ARTICLE_4, TEST_HASHTAG_6)

        private val TEST_ARTICLE_OPTION_1 = ArticleOption(TEST_ARTICLE_1, TEST_OPTION_1)
        private val TEST_ARTICLE_OPTION_2 = ArticleOption(TEST_ARTICLE_1, TEST_OPTION_2)
        private val TEST_ARTICLE_OPTION_3 = ArticleOption(TEST_ARTICLE_1, TEST_OPTION_6)
        private val TEST_ARTICLE_OPTION_4 = ArticleOption(TEST_ARTICLE_2, TEST_OPTION_4)
        private val TEST_ARTICLE_OPTION_5 = ArticleOption(TEST_ARTICLE_2, TEST_OPTION_7)
        private val TEST_ARTICLE_OPTION_6 = ArticleOption(TEST_ARTICLE_3, TEST_OPTION_4)
        private val TEST_ARTICLE_OPTION_7 = ArticleOption(TEST_ARTICLE_3, TEST_OPTION_5)
        private val TEST_ARTICLE_OPTION_8 = ArticleOption(TEST_ARTICLE_3, TEST_OPTION_6)
        private val TEST_ARTICLE_OPTION_9 = ArticleOption(TEST_ARTICLE_4, TEST_OPTION_1)
        private val TEST_ARTICLE_OPTION_10 = ArticleOption(TEST_ARTICLE_4, TEST_OPTION_6)

        private val TEST_COMMENT_1 = Comment(TEST_ARTICLE_1, TEST_USER_1, true, "테스트 댓글 1 입니다.")
        private val TEST_COMMENT_2 = Comment(TEST_ARTICLE_1, TEST_USER_1, false, "테스트 댓글 2 입니다.")
        private val TEST_COMMENT_3 = Comment(TEST_ARTICLE_1, TEST_USER_2, false, "테스트 댓글 3 입니다.")
        private val TEST_COMMENT_4 = Comment(TEST_ARTICLE_2, TEST_USER_1, false, "테스트 댓글 4 입니다.")
        private val TEST_COMMENT_5 = Comment(TEST_ARTICLE_3, TEST_USER_2, true, "테스트 댓글 5 입니다.")
    }
}
