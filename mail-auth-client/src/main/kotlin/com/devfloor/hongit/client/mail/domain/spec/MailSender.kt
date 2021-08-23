package com.devfloor.hongit.client.mail.domain.spec

import org.springframework.mail.SimpleMailMessage
import org.springframework.scheduling.annotation.Async

interface MailSender {
    @Async
    fun send(message: SimpleMailMessage)
}
