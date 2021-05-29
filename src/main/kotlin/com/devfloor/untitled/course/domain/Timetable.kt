package com.devfloor.untitled.course.domain

import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * 수업 시간표를 관리하는 embedded 객체
 *
 * @property courseDay 수업 요일
 * @property courseTime 수업 시간
 * @property text 수업 시간표 문자열
 */
@Embeddable
class Timetable(
    courseDay: CourseDay,
    courseTime: CourseTime,
) {
    @Column(name = "course_day")
    val courseDay: CourseDay = courseDay

    @Column(name = "course_time")
    val courseTime: CourseTime = courseTime

    val text: String
        get() = "${courseDay.text}${courseTime.number}"

    private fun verifyText(text: String): Boolean =
        courseDay.text == text.substring(0, 1) && courseTime.number == text.substring(1).toLong()

    companion object {
        private val CACHE: List<Timetable> = enumValues<CourseDay>()
            .flatMap { courseDay ->
                enumValues<CourseTime>().map { Timetable(courseDay, it) }
            }

        fun from(text: String) = CACHE.first { it.verifyText(text) }
    }
}
