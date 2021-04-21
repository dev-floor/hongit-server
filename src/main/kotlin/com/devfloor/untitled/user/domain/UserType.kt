package com.devfloor.untitled.user.domain

/**
 * 회원의 유형을 관리하는 enum
 *
 * @property text 좋아요 유형
 */
enum class UserType(val text: String) {
    STUDENT("재학생"),
    GRADUATE("졸업생"),
}