package com.devfloor.hongit.client.mail.application.request

import com.devfloor.hongit.core.user.domain.User
import org.springframework.util.Assert

data class MailSendRequest(
    val userId: Long,
    val receiverEmail: String,
) {
    init {
        Assert.hasText(receiverEmail, "메일을 보낼 주소가 존재하지 않습니다 - receiverEmail: $receiverEmail")
    }

    constructor(user: User) : this(user.id, user.email.toEmailString())
}
