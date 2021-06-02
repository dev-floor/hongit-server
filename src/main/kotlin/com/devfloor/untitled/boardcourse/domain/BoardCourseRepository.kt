package com.devfloor.untitled.boardcourse.domain

import com.devfloor.untitled.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

/**
 * 게시판과 수업의 연관관계를 관리하는 repository
 */
@Transactional(readOnly = true)
interface BoardCourseRepository : JpaRepository<BoardCourse, Long> {
    fun findAllByBoard(board: Board): List<BoardCourse>
}
