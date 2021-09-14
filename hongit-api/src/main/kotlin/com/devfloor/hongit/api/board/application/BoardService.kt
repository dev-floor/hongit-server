package com.devfloor.hongit.api.board.application

import com.devfloor.hongit.api.board.application.response.BoardResponse
import com.devfloor.hongit.api.board.application.response.BoardSimpleResponse
import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.course.application.CourseService
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.board.domain.BoardRepository
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.boardoption.domain.BoardOptionRepository
import com.devfloor.hongit.core.bookmarkboard.domain.BoardBookmarkRepository
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val boardOptionRepository: BoardOptionRepository,
    private val boardBookmarkRepository: BoardBookmarkRepository,

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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    fun showAllBoardByBoardType(type: BoardType): List<BoardResponse> {
        log.info("[BoardService.showAllBoardByBoardType] 수업 게시판 선택 화면 조회 - type: $type")
        return boardRepository.findAllByType(type)
            .map {
                val course = courseService.showAllByBoard(it)
                    .firstOrNull()
                BoardResponse(it, course)
            }
            .also { log.info("[BoardService.showAllBoardByBoardType] 수업 게시판 선택 화면 조회 - response: $it") }
    }
}
