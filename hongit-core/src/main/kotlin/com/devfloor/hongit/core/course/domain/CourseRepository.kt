package com.devfloor.hongit.core.course.domain

import com.devfloor.hongit.core.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository : JpaRepository<Course, Long> {
    fun findAllByBoard(board: Board): List<Course>
}
