package com.devfloor.untitled.boardcourse.domain

import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.course.domain.Course
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 게시판과 수업의 연관관계 매핑 entity
 *
 * @property id 아이디
 * @property board 게시판
 * @property course 수업
 */
@Entity
@Table(name = "board_courses")
class BoardCourse(
    board: Board,
    course: Course,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_course_id")
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "board_id")
    val board: Board = board

    @ManyToOne
    @JoinColumn(name = "course_id")
    val course: Course = course
}
