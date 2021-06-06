package com.devfloor.untitled.option.domain

import com.devfloor.untitled.common.domain.BaseEnum

/**
 * 옵션의 유형을 관리하는 enum
 *
 * @property text 옵션의 유형
 */
enum class OptionType(
    override val text: String,
) : BaseEnum {
    COURSE_GROUP("분반"),
    ARTICLE_KIND("게시글 종류")
    ;

    override val id: String
        get() = this.name
}
