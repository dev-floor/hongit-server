package com.devfloor.untitled.board.presentation

import com.devfloor.untitled.board.application.BoardService
import com.devfloor.untitled.board.application.response.BoardResponse
import com.devfloor.untitled.board.presentation.BoardController.Companion.BOARD_API_URI
import com.devfloor.untitled.common.config.Slf4j
import com.devfloor.untitled.common.config.Slf4j.Companion.log
import com.devfloor.untitled.common.utils.BASE_API_URI
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping(value = [BOARD_API_URI])
class BoardController(
    private val boardService: BoardService,
) {
    @GetMapping(value = ["/{boardId}"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showByBoardId(@PathVariable boardId: Long): BoardResponse {
        log.info("[BoardController.showByBoardId] 게시판 상세정보 조회 - url: $BOARD_API_URI/$boardId")
        return boardService.showByBoardId(boardId)
            .also { log.info("[BoardController.showByBoardId] 게시판 상세정보 조회 완료 - response: $it") }
    }

    companion object {
        const val BOARD_API_URI = "$BASE_API_URI/boards"
    }
}
