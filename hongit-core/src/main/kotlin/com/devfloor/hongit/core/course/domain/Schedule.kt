package com.devfloor.hongit.core.course.domain

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

/**
 * 수업 시간표 중 단건에 대한 정보를 관리하는 객체 (ex. 월1)
 *
 * @property courseDay 수업 요일
 * @property courseTime 수업 시간
 */
@Embeddable
@Table(name = "schedules")
class Schedule private constructor(
    courseDay: CourseDay,
    courseTime: CourseTime,
) {
    @Enumerated(value = EnumType.STRING)
    @Column(name = "course_day")
    val courseDay: CourseDay = courseDay

    @Enumerated(value = EnumType.STRING)
    @Column(name = "course_time")
    val courseTime: CourseTime = courseTime

    val text: String
        get() = "${courseDay.text}${courseTime.number}"

    private fun verifyText(text: String): Boolean =
        courseDay.text == text.substring(0, 1) && courseTime.number == text.substring(1).toLong()

    companion object {
        private val CACHE: List<Schedule> =
            enumValues<CourseDay>().flatMap { courseDay ->
                enumValues<CourseTime>().map { Schedule(courseDay, it) }
            }

        fun from(text: String) = CACHE.first { it.verifyText(text) }
    }
}
