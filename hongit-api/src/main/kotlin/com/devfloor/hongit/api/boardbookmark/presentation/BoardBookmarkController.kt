package com.devfloor.hongit.api.boardbookmark.presentation

import com.devfloor.hongit.api.boardbookmark.application.BoardBookmarkService
import com.devfloor.hongit.api.boardbookmark.presentation.BoardBookmarkController.Companion.BOARD_BOOKMARK_URI
import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.security.core.LoginUser
import com.devfloor.hongit.core.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [BOARD_BOOKMARK_URI])
class BoardBookmarkController(
    private val boardBookmarkService: BoardBookmarkService,
) {
    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun modifyAllByBoardIds(
        @RequestBody boardIds: List<Long>,
        @LoginUser loginUser: User,
    ) = boardBookmarkService.modifyAllByBoardIds(boardIds, loginUser)

    companion object {
        const val BOARD_BOOKMARK_URI = "$BASE_API_URI/board-bookmarks"
    }
}
