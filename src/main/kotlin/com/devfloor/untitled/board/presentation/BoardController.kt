package com.devfloor.untitled.board.presentation

import com.devfloor.untitled.board.application.BoardFacade
import com.devfloor.untitled.board.application.BoardResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardController(
    private val boardFacade: BoardFacade
) {
    @GetMapping(value = ["/articles"]) // TODO() : 스크린 구성 이후 URI, pathVariable 수정 예정
    @ResponseStatus(value = HttpStatus.OK)
    fun showAll(): BoardResponse = boardFacade.showByBoardId()
}
