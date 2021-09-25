package com.devfloor.hongit.api.user.application.request

import com.devfloor.hongit.api.common.utils.ApiUtils
import com.devfloor.hongit.core.user.domain.Email
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserType
import org.springframework.util.StringUtils

data class SignUpRequest(
    val username: String,
    val nickname: String,
    val password: String,
    val checkedPassword: String,
    val email: String,
    val type: UserType,
    val classOf: String,
    val approved: Boolean,
) {
    fun verifyInfo(): Boolean = StringUtils.hasText(username) &&
        StringUtils.hasText(nickname) &&
        StringUtils.hasText(password) &&
        StringUtils.hasText(checkedPassword) &&
        StringUtils.hasText(email) &&
        StringUtils.hasText(classOf) &&
        approved

    fun verifyPassword(): Boolean = this.password == this.checkedPassword

    fun toUser(): User = User(
        username = username,
        password = password,
        nickname = nickname,
        email = Email.from(email),
        type = type,
        classOf = classOf,
    )

    override fun toString(): String =
        "SignUpRequest(" +
            "username=$username, " +
            "nickname=$nickname, " +
            "password=${ApiUtils.mask(password)}, " +
            "checkedPassword=${ApiUtils.mask(checkedPassword)}, " +
            "email=$email, " +
            "type=$type, " +
            "classOf=$classOf, " +
            "approved=$approved" +
            ")"
}
