package com.devfloor.untitled.course.domain

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Timetable(
    courseDay: CourseDay,
    courseTime: CourseTime,
) {
    @Column(name = "course_day")
    val courseDay: CourseDay = courseDay

    @Column(name = "course_time")
    val courseTime: CourseTime = courseTime

    val text: String = "${courseDay.text}${courseTime.number}"

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
