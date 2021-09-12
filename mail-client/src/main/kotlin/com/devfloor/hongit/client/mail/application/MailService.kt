package com.devfloor.hongit.client.mail.application

import com.devfloor.hongit.client.mail.application.request.MailRequest
import com.devfloor.hongit.client.mail.domain.spec.MailSender
import com.devfloor.hongit.core.authtoken.AuthToken
import com.devfloor.hongit.core.authtoken.AuthTokenRepository
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
class MailService(
    private val authTokenRepository: AuthTokenRepository,
    private val mailSender: MailSender,
) {
    @Async
    @Transactional
    fun sendAuthenticationMail(request: MailRequest) {
        log.info("[AuthMailService.sendAuthenticationMail] 인증메일 발송 요청 - request: $request")

        val tokenId = authTokenRepository.save(AuthToken())
            .id
            .toString()
        val message = mailSender.createMimeMessage(request, tokenId)

        mailSender.send(message)
        log.info("[AuthMailService.sendAuthenticationMail] 인증메일 발송 완료")
    }
}
