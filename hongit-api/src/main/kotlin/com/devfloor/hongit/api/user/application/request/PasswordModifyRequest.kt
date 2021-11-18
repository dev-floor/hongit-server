package com.devfloor.hongit.api.user.application.request

data class PasswordModifyRequest(
    val oldPassword: String,
    val newPassword: String,
    val checkedNewPassword: String,
) {
    fun matchesPassword() = newPassword == checkedNewPassword
}
