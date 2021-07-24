package com.devfloor.hongit.api.user.application.request

import com.devfloor.hongit.core.user.domain.UserType

data class JoinRequest(
    val username: String,
    val nickname: String,
    val password: String,
    val checkedPassword: String,
    val type: UserType,
    val classOf: String,
    val email: String,
    val approved: Boolean,
) {
    fun verifyPassword(): Boolean = this.password == this.checkedPassword
}
