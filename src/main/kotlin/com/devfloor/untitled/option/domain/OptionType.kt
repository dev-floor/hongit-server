package com.devfloor.untitled.option.domain

/**
 * 옵션의 유형을 관리하는 enum
 *
 * @property text 좋아요 유형
 */
enum class OptionType(private val text: String) {
    CLASS_ONE(text = "1분반"),
    CLASS_TWO(text = "2분반"),
    CLASS_THREE(text = "3분반"),
    CLASS_FOUR(text = "4분반"),
}
