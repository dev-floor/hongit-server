package com.devfloor.hongit.core.course.domain

import com.devfloor.hongit.core.common.domain.BaseEnum

/**
 * 학기의 종류를 관리하는 enum
 *
 * @property text 학기 설명
 */
enum class Semester(
    override val text: String,
) : BaseEnum {
    FIRST_SEMESTER("1학기"),
    SUMMER_SEMESTER("여름계절학기"),
    SECOND_SEMESTER("2학기"),
    WINTER_SEMESTER("겨울계절학기"),
    ;

    override val id: String
        get() = this.name
}
