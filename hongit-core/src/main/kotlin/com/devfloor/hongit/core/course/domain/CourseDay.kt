package com.devfloor.hongit.core.course.domain

import com.devfloor.hongit.core.common.domain.BaseEnum

/**
 * 수업 요일을 관리하는 enum
 *
 * @property text 요일
 */
enum class CourseDay(
    override val text: String,
) : BaseEnum {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일"),
    ;

    override val id: String
        get() = this.name

    companion object {
        fun from(text: String) = values().first { it.text == text }
    }
}
