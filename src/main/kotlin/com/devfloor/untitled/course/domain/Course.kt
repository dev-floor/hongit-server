package com.devfloor.untitled.course.domain

import com.devfloor.untitled.common.domain.BaseEntity
import com.devfloor.untitled.common.domain.Grade
import com.devfloor.untitled.common.domain.OpeningSemester
import com.devfloor.untitled.option.domain.Option
import com.devfloor.untitled.professor.domain.Professor
import com.devfloor.untitled.subject.domain.Subject
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
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
 */
@Entity
@Table(name = "courses")
class Course(
    code: String,
    openingSemester: OpeningSemester,
    professor: Professor,
    subject: Subject,
    grade: Grade,
    option: Option,
    timetable: Timetable,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    val id: Long = 0

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
    val timetable: Timetable = timetable

    init {
        TODO("option 유효성 검증 로직 추가")
    }
}
