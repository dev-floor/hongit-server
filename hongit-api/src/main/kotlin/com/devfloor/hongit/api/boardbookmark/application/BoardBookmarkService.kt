package com.devfloor.hongit.api.boardbookmark.application

import com.devfloor.hongit.core.board.domain.BoardRepository
import com.devfloor.hongit.core.bookmarkboard.domain.BoardBookmark
import com.devfloor.hongit.core.bookmarkboard.domain.BoardBookmarkRepository
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.user.domain.User
import org.springframework.stereotype.Service

@Slf4j
@Service
class BoardBookmarkService(
    private val boardRepository: BoardRepository,
    private val boardBookmarkRepository: BoardBookmarkRepository,
) {
    fun modifyAllByBoardIds(boardIds: List<Long>, loginUser: User) {
        boardBookmarkRepository.deleteAllByUser(loginUser)
        boardRepository.findAllById(boardIds)
            .map { BoardBookmark(board = it, user = loginUser) }
            .let(boardBookmarkRepository::saveAll)
    }
}
