package com.devfloor.hongit.core.boardoption.domain

import com.devfloor.hongit.core.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface BoardOptionRepository : JpaRepository<BoardOption, Long> {
    fun findAllByBoard(board: Board): List<BoardOption>
}
