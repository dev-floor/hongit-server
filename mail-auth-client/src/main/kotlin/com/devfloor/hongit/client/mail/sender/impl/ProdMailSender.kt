package com.devfloor.hongit.client.mail.sender.impl

import com.devfloor.hongit.client.mail.sender.dto.MailRequest
import com.devfloor.hongit.client.mail.sender.spec.MailSender
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender

@Slf4j
class ProdMailSender(
    private val sender: JavaMailSender,
) : MailSender {
    override fun send(request: MailRequest): Unit = try {
        log.info("[ProdMailSender] 메일 발송 - request: $request")

        sender.send(request.toMessage())

        log.info("[ProdMailSender] 메일 발송 완료")
    } catch (e: MailException) {
        log.error("[ProdMailSender] 메일 발송 실패 - errorMessage: ${e.message}")
        throw e
    }
}
