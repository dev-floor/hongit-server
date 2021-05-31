package com.devfloor.untitled.boardcourse.domain

import com.devfloor.untitled.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardCourseRepository : JpaRepository<BoardCourse, Long> {
    fun findAllByBoard(board: Board): List<BoardCourse>
}
