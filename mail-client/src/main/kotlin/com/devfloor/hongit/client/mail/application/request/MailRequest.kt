package com.devfloor.hongit.client.mail.application.request

import org.springframework.util.Assert

data class MailRequest(
    val receiverEmail: String,
) {
    init {
        Assert.hasText(receiverEmail, "메일을 보낼 주소가 존재하지 않습니다 - receiverEmail: $receiverEmail")
    }
}
