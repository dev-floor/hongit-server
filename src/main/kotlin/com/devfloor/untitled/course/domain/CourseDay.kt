package com.devfloor.untitled.course.domain

/**
 * 수업 요일을 관리하는 enum
 *
 * @property text 요일
 */
enum class CourseDay(
    val text: String,
) {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일"),
    ;

    companion object {
        fun from(text: String) = values().first { it.text == text }
    }
}
