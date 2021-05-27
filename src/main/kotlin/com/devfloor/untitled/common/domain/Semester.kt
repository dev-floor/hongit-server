package com.devfloor.untitled.common.domain

/**
 * 학기의 종류를 관리하는 enum
 *
 * @property text 학기 설명
 */
enum class Semester(
    val text: String,
) {
    FIRST_SEMESTER("1학기"),
    SUMMER_SEMESTER("여름계절학기"),
    SECOND_SEMESTER("2학기"),
    WINTER_SEMESTER("겨울계절학기"),
    ;
}
