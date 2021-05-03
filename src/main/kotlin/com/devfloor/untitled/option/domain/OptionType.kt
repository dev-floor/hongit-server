package com.devfloor.untitled.option.domain

/**
 * 옵션의 유형을 관리하는 enum
 *
 * @property text 좋아요 유형
 */
enum class OptionType(val text: String) {
    CLASS_ONE("1분반"),
    CLASS_TWO("2분반"),
    CLASS_THREE("3분반"),
    CLASS_FOUR("4분반"),
    ;

    companion object {
        fun from(text: String): OptionType {
            return values().first { it.text == text }
        }
    }
}
