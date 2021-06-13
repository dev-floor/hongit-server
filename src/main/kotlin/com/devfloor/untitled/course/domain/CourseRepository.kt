package com.devfloor.untitled.course.domain

import com.devfloor.untitled.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository : JpaRepository<Course, Long> {
    fun findAllByBoard(board: Board): List<Course>
}
