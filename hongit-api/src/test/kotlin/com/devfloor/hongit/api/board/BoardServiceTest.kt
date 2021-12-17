package com.devfloor.hongit.api.board

import com.devfloor.hongit.api.board.application.BoardService
import com.devfloor.hongit.api.board.application.response.BoardResponse
import com.devfloor.hongit.api.board.application.response.BoardSimpleResponse
import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.course.application.CourseService
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.api.support.TestFixtures.BoardFixture.BOARD_1
import com.devfloor.hongit.api.support.TestFixtures.BoardFixture.COURSE_BOARD_1
import com.devfloor.hongit.api.support.TestFixtures.BoardFixture.COURSE_BOARD_2
import com.devfloor.hongit.api.support.TestFixtures.BoardOptionFixture.BOARD_OPTION_1
import com.devfloor.hongit.api.support.TestFixtures.BoardOptionFixture.BOARD_OPTION_2
import com.devfloor.hongit.api.support.TestFixtures.CourseFixture.COURSE_1
import com.devfloor.hongit.api.support.TestFixtures.CourseFixture.COURSE_2
import com.devfloor.hongit.core.board.domain.BoardRepository
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.boardoption.domain.BoardOptionRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class BoardServiceTest {
    @Mock
    private lateinit var boardRepository: BoardRepository

    @Mock
    private lateinit var boardOptionRepository: BoardOptionRepository

    @Mock
    private lateinit var courseService: CourseService

    private lateinit var boardService: BoardService

    @BeforeEach
    fun setUp() {
        boardService = BoardService(boardRepository, boardOptionRepository, courseService)
    }

    @Test
    internal fun `showByBoardId - 게시판 상세정보 조회에 성공하고 BoardResponse를 반환한다`() {
        val courses = listOf(COURSE_1, COURSE_2)
        val boardOptions = listOf(BOARD_OPTION_1, BOARD_OPTION_2)
        val options = boardOptions.map { it.option }
        `when`(boardRepository.findById(any())).thenReturn(Optional.of(BOARD_1))
        `when`(courseService.showAllByBoard(any())).thenReturn(courses)
        `when`(boardOptionRepository.findAllByBoard(any())).thenReturn(boardOptions)

        val result = boardService.showByBoardId(0)

        assertThat(result).isExactlyInstanceOf(BoardResponse::class.java)
        assertThat(result).isEqualTo(
            BoardResponse(
                board = BOARD_1,
                course = courses.firstOrNull(),
                options = options + courses.map { it.option },
            )
        )
    }

    @Test
    internal fun `showByBoardId - id에 해당하는 board가 존재하지 않는 경우 게시판 상세정보 조회에 실패한다`() {
        `when`(boardRepository.findById(any())).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 Board이(가) 존재하지 않습니다 - BoardId: 0"

        Assertions.assertThatThrownBy { boardService.showByBoardId(0) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `showAll - 게시판 목록 조회에 성공하고 BoardSimpleResponse list를 반환한다`() {
        val boards = listOf(BOARD_1, COURSE_BOARD_1)
        val courses = listOf(COURSE_1, COURSE_2)
        `when`(boardRepository.findAll()).thenReturn(boards)
        `when`(courseService.showAllByBoard(any())).thenReturn(courses)

        val result = boardService.showAll()

        result.forEach { assertThat(it).isExactlyInstanceOf(BoardSimpleResponse::class.java) }
        assertThat(result).isEqualTo(
            boards.map {
                BoardSimpleResponse(board = it, grade = courses.firstOrNull()?.grade)
            }
        )
    }

    @Test
    internal fun `showAllBoardByBoardType - 수업 게시판 선택 화면 조회에 성공하고 BoardResponse list를 반환한다`() {
        val boards = listOf(COURSE_BOARD_1, COURSE_BOARD_2)
        val courses = listOf(COURSE_1, COURSE_2)
        `when`(boardRepository.findAllByType(any())).thenReturn(boards)
        `when`(courseService.showAllByBoard(any())).thenReturn(courses)

        val result = boardService.showAllBoardByBoardType(BoardType.COURSE_BOARD)

        result.forEach { assertThat(it).isExactlyInstanceOf(BoardResponse::class.java) }
        assertThat(result).isEqualTo(
            boards.map {
                BoardResponse(board = it, course = courses.firstOrNull())
            }
        )
    }
}
