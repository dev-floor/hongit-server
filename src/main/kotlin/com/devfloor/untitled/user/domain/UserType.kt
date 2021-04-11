package com.devfloor.untitled.user.domain

/**
 * 회원의 유형을 관리하는 enum
 */
enum class UserType(val type: String) {
    STUDENT("재학생"),
    GRADUATE("졸업생"),
}