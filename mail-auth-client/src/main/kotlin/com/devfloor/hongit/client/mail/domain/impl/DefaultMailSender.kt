package com.devfloor.hongit.client.mail.domain.impl

import com.devfloor.hongit.client.mail.application.request.MailSendRequest
import com.devfloor.hongit.client.mail.domain.spec.MailSender
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.mail.javamail.JavaMailSender
import javax.mail.internet.MimeMessage

@Slf4j
class DefaultMailSender(
    private val sender: JavaMailSender,
) : MailSender {
    override fun send(message: MimeMessage) {
        log.info("[DefaultMailSender.send] 메일 발송 완료")
    }

    override fun createMimeMessage(request: MailSendRequest, tokenId: String): MimeMessage = sender.createMimeMessage()
        .also {
            log.info("[DefaultMailSender.createMimeMessage] MimeMessage 생성 완료 - " +
                "receiverEmail: ${request.receiverEmail}, tokenId: $tokenId")
        }
}
