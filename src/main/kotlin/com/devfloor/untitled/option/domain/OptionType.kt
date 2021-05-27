package com.devfloor.untitled.option.domain

/**
 * 옵션의 유형을 관리하는 enum
 *
 * @property text 옵션의 유형
 */
enum class OptionType(text: String) {
    COURSE_GROUP("분반"),
    ARTICLE_KIND("게시글 종류")
    ;

    val text: String = text

    companion object {
        fun from(text: String): OptionType {
            return values().first { it.text == text }
        }
    }
}
