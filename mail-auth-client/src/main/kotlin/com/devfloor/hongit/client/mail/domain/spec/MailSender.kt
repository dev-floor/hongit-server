package com.devfloor.hongit.client.mail.domain.spec

import com.devfloor.hongit.client.mail.application.request.MailSendRequest
import org.springframework.scheduling.annotation.Async
import javax.mail.internet.MimeMessage

interface MailSender {
    @Async
    fun send(message: MimeMessage)

    fun createMimeMessage(request: MailSendRequest, tokenId: String): MimeMessage
}
