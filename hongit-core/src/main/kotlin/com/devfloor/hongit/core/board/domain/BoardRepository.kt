package com.devfloor.hongit.core.board.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface BoardRepository : JpaRepository<Board, Long> {
    fun findAllByType(type: BoardType): List<Board>
    fun findByTypeNot(boardType: BoardType): List<Board>
}
