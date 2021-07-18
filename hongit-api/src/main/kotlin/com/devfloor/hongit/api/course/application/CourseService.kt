package com.devfloor.hongit.api.course.application

import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.course.domain.Course
import com.devfloor.hongit.core.course.domain.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(
    private val courseRepository: CourseRepository,
) {
    fun showAllByBoard(board: Board): List<Course> = courseRepository.findAllByBoard(board)
        .also { validateCourse(it) }

    private fun validateCourse(courses: List<Course>) {
        val first = courses.firstOrNull() ?: return

        courses.stream()
            .allMatch { it.matchesRequiredInfo(first) }
            .let { if (!it) throw IllegalArgumentException("수업 정보가 동일하지 않습니다.") }
    }
}
