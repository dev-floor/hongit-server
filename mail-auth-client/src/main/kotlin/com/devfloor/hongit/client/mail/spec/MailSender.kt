package com.devfloor.hongit.client.mail.spec

import com.devfloor.hongit.client.mail.dto.MailRequest
import org.springframework.scheduling.annotation.Async

interface MailSender {
    @Async
    fun send(request: MailRequest)
}
