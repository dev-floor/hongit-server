package com.devfloor.hongit.client.mail.sender.spec

import com.devfloor.hongit.client.mail.sender.dto.MailRequest
import org.springframework.scheduling.annotation.Async

interface MailSender {
    @Async
    fun send(request: MailRequest)
}
