package com.devfloor.hongit.core.course.domain

import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.common.domain.BaseEntity
import com.devfloor.hongit.core.option.domain.Option
import com.devfloor.hongit.core.option.domain.OptionType
import com.devfloor.hongit.core.professor.domain.Professor
import com.devfloor.hongit.core.subject.domain.Subject
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 수업 정보를 관리하는 entity
 *
 * @property id 아이디
 * @property code 학수번호
 * @property openingSemester 개설학기
 * @property professor 교수
 * @property subject 수업
 * @property grade 학년
 * @property option 옵션(optionType = 분반)
 * @property timetable 시간표
 * @property board 게시판
 */
@Entity
@Table(
    name = "courses",
    indexes = [
        Index(name = "idx_board_id", columnList = "board_id")
    ]
)
class Course(
    code: String,
    openingSemester: OpeningSemester,
    professor: Professor,
    subject: Subject,
    grade: Grade,
    option: Option,
    timetable: Timetable,
    board: Board? = null,
    id: Long = 0,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    val id: Long = id

    @Column(name = "code")
    val code: String = code

    @Embedded
    @Column(name = "opening_semester")
    val openingSemester: OpeningSemester = openingSemester

    @ManyToOne
    @JoinColumn(name = "professor_id")
    val professor: Professor = professor

    @ManyToOne
    @JoinColumn(name = "subject_id")
    val subject: Subject = subject

    @Enumerated(value = EnumType.STRING)
    @Column(name = "grade")
    val grade: Grade = grade

    @ManyToOne
    @JoinColumn(name = "option_id")
    val option: Option = option

    @Embedded
    @Column(name = "timetable")
    var timetable: Timetable = timetable
        protected set

    @ManyToOne
    @JoinColumn(name = "board_id")
    var board: Board? = board
        protected set

    init {
        assert(option.type == OptionType.COURSE_GROUP) {
            "course의 option은 optionType이 COURSE_GROUP인 옵션만 가능합니다."
        }
    }

    fun withId(id: Long): Course =
        Course(code, openingSemester, professor, subject, grade, option, timetable, board, id)

    fun updateBoard(board: Board) {
        this.board = board
    }

    fun matchesRequiredInfo(course: Course): Boolean =
        this.openingSemester == course.openingSemester &&
            this.professor == course.professor &&
            this.subject == course.subject &&
            this.grade == course.grade
}
