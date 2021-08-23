package com.devfloor.hongit.client.mail.sender.spec

import org.springframework.mail.SimpleMailMessage
import org.springframework.scheduling.annotation.Async

interface MailSender {
    @Async
    fun send(message: SimpleMailMessage)
}
