package com.devfloor.hongit.client.mail.domain.spec

import org.springframework.scheduling.annotation.Async
import javax.mail.internet.MimeMessage

interface MailSender {
    @Async
    fun send(message: MimeMessage)

    fun createMimeMessage(receiverEmail: String, tokenId: String): MimeMessage
}
