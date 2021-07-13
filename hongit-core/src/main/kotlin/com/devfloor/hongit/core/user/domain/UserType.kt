package com.devfloor.hongit.core.user.domain

import com.devfloor.hongit.core.common.domain.BaseEnum

/**
 * 회원의 유형을 관리하는 enum
 *
 * @property text 좋아요 유형
 */
enum class UserType(
    override val text: String,
) : BaseEnum {
    STUDENT("재학생"),
    GRADUATE("졸업생"),
    ;

    override val id: String
        get() = this.name
}
