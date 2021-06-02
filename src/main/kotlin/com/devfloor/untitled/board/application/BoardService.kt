package com.devfloor.untitled.board.application

import com.devfloor.untitled.board.application.response.BoardResponse
import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.board.domain.BoardRepository
import com.devfloor.untitled.boardcourse.domain.BoardCourseRepository
import com.devfloor.untitled.boardoption.domain.BoardOptionRepository
import com.devfloor.untitled.common.config.Slf4j
import com.devfloor.untitled.common.config.Slf4j.Companion.log
import com.devfloor.untitled.common.exception.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val boardCourseRepository: BoardCourseRepository,
    private val boardOptionRepository: BoardOptionRepository,
) {
    @Transactional(readOnly = true)
    fun showByBoardId(boardId: Long): BoardResponse {
        log.info("[BoardService.showByBoardId] 게시판 상세정보 조회 - boardId: $boardId")

        val board = boardRepository.findByIdOrNull(boardId)
            ?: EntityNotFoundException.notExistsId(Board::class, boardId)
        val courses = boardCourseRepository.findAllByBoard(board)
            .map { it.course }
        val options = boardOptionRepository.findAllByBoard(board)
            .map { it.option }

        return BoardResponse.of(
            board = board,
            grade = courses.firstOrNull()?.grade,
            options = options + courses.map { it.option },
        ).also { log.info("[BoardService.showByBoardId] 게시판 상세정보 조회 완료 - response: $it") }
    }
}
