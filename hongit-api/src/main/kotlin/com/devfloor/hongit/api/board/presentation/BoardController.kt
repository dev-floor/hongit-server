package com.devfloor.hongit.api.board.presentation

import com.devfloor.hongit.api.board.application.BoardService
import com.devfloor.hongit.api.board.application.response.BoardResponse
import com.devfloor.hongit.api.board.application.response.BoardSimpleResponse
import com.devfloor.hongit.api.board.presentation.BoardController.Companion.BOARD_API_URI
import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.security.core.LoginUser
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import com.devfloor.hongit.core.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    fun showAll(): List<BoardSimpleResponse> {
        log.info("[BoardController.showAll] 게시판 목록 조회 - url: $BOARD_API_URI")
        return boardService.showAll()
            .also { log.info("[BoardController.showAll] 게시판 목록 조회 완료 - response: $it") }
    }

    @GetMapping(params = ["type"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllBoardByBoardType(@RequestParam type: BoardType): List<BoardResponse> {
        log.info("[BoardController.showAllBoardByBoardType] 수업 게시판 선택 화면 조회 - type: $type")

        return boardService.showAllBoardByBoardType(type)
            .also { log.info("[BoardController.showAllBoardByBoardType] 수업 게시판 선택 화면 조회 완료 - response: $it") }
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun updateAllBookmarks(
        @RequestBody boardIds: List<Long>,
        @LoginUser user: User,
    ) = boardService.updateAllBookmarks(boardIds, user)

    companion object {
        const val BOARD_API_URI = "$BASE_API_URI/boards"
    }
}
