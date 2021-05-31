package com.devfloor.untitled.boardoption.domain

import com.devfloor.untitled.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardOptionRepository : JpaRepository<BoardOption, Long> {
    fun findAllByBoard(board: Board): List<BoardOption>
}
