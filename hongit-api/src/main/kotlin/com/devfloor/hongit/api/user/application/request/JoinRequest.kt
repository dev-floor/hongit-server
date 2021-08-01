package com.devfloor.hongit.api.user.application.request

import com.devfloor.hongit.core.user.domain.Email
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserType
import org.springframework.util.StringUtils

data class JoinRequest(
    val username: String,
    val nickname: String,
    val password: String,
    val checkedPassword: String,
    val email: String,
    val type: UserType,
    val classOf: String,
    val approved: Boolean,
) {
    fun verifyInfo(): Boolean = StringUtils.hasText(username)
        && StringUtils.hasText(nickname)
        && StringUtils.hasText(password)
        && StringUtils.hasText(checkedPassword)
        && StringUtils.hasText(email)
        && StringUtils.hasText(classOf)
        && approved

    fun verifyPassword(): Boolean = this.password == this.checkedPassword

    fun toUser(): User = User(
        username = username,
        password = password,
        nickname = nickname,
        email = Email.from(email),
        type = type,
        classOf = classOf,
    )
}
