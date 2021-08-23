package com.devfloor.hongit.client.mail.sender.impl

import com.devfloor.hongit.client.mail.sender.spec.MailSender
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.mail.SimpleMailMessage

@Slf4j
class DefaultMailSender : MailSender {
    override fun send(message: SimpleMailMessage) {
        log.info("[DefaultMailSender] 메일 전송 완료 - to: ${message.to}, subject: ${message.subject}, content: ${message.text}")
    }
}
