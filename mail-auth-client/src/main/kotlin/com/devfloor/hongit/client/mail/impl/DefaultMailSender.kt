package com.devfloor.hongit.client.mail.impl

import com.devfloor.hongit.client.mail.dto.MailRequest
import com.devfloor.hongit.client.mail.spec.MailSender
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log

@Slf4j
class DefaultMailSender : MailSender {
    override fun send(request: MailRequest) {
        log.info("[DefaultMailSender] 메일 전송 완료 - request: $request")
    }
}
