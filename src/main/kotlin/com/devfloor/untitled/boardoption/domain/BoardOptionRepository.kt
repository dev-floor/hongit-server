package com.devfloor.untitled.boardoption.domain

import com.devfloor.untitled.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

/**
 * 게시판과 옵션의 연관관계를 관리하는 repository
 */
@Transactional(readOnly = true)
interface BoardOptionRepository : JpaRepository<BoardOption, Long> {
    fun findAllByBoard(board: Board): List<BoardOption>
}
