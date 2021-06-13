package com.devfloor.untitled.board.application

import com.devfloor.untitled.board.application.response.BoardResponse
import com.devfloor.untitled.board.application.response.BoardSimpleResponse
import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.board.domain.BoardRepository
import com.devfloor.untitled.boardoption.domain.BoardOptionRepository
import com.devfloor.untitled.common.config.Slf4j
import com.devfloor.untitled.common.config.Slf4j.Companion.log
import com.devfloor.untitled.common.exception.EntityNotFoundException
import com.devfloor.untitled.course.application.CourseService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val boardOptionRepository: BoardOptionRepository,

    private val courseService: CourseService,
) {
    @Transactional(readOnly = true)
    fun showByBoardId(boardId: Long): BoardResponse {
        log.info("[BoardService.showByBoardId] 게시판 상세정보 조회 - boardId: $boardId")

        val board = boardRepository.findByIdOrNull(boardId)
            ?: EntityNotFoundException.notExistsId(Board::class, boardId)
        val courses = courseService.showAllByBoard(board)

        val options = boardOptionRepository.findAllByBoard(board)
            .map { it.option }

        return BoardResponse(
            board = board,
            course = courses.firstOrNull(),
            options = options + courses.map { it.option },
        ).also { log.info("[BoardService.showByBoardId] 게시판 상세정보 조회 완료 - response: $it") }
    }

    fun showAll(): List<BoardSimpleResponse> {
        log.info("[BoardService.showAll] 게시판 목록 조회")
        return boardRepository.findAll()
            .map { board ->
                val courses = courseService.showAllByBoard(board)

                BoardSimpleResponse(
                    board = board,
                    grade = courses.firstOrNull()?.grade
                )
            }.also { log.info("[BoardService.showAll] 게시판 목록 조회 완료 - response: $it") }
    }
}
