package com.devfloor.untitled.course.domain

import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Embeddable
import javax.persistence.JoinColumn

/**
 * 수업 시간표를 관리하는 embedded 객체
 *
 * @property timetable 수업 시간표 목록
 */
@Embeddable
class Timetable(
    timetable: List<Schedule>,
) {
    @ElementCollection
    @CollectionTable(
        name = "schedules",
        joinColumns = [JoinColumn(name = "course_id")]
    )
    @Column(name = "timetable")
    val timetable: List<Schedule> = timetable
}
