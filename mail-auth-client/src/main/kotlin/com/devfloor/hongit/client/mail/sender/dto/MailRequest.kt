package com.devfloor.hongit.client.mail.sender.dto

import org.springframework.mail.SimpleMailMessage

data class MailRequest(
    val sendTo: String,
    val subject: String,
    val content: String,
) {
    fun toMessage(): SimpleMailMessage = SimpleMailMessage().also {
        it.setTo(sendTo)
        it.subject = subject
        it.text = content
    }
}
