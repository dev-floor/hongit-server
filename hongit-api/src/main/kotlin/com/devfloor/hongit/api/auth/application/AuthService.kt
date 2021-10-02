package com.devfloor.hongit.api.auth.application

import com.devfloor.hongit.api.auth.application.request.AuthMailRequest
import com.devfloor.hongit.api.auth.exception.InvalidAuthException
import com.devfloor.hongit.client.mail.domain.spec.MailSender
import com.devfloor.hongit.core.authtoken.AuthToken
import com.devfloor.hongit.core.authtoken.AuthTokenRepository
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Slf4j
@Service
class AuthService(
    private val authTokenRepository: AuthTokenRepository,
    private val mailSender: MailSender,
) {
    @Async
    @Transactional
    fun sendAuthenticationMail(request: AuthMailRequest) {
        log.info("[AuthMailService.sendAuthenticationMail] 인증메일 발송 요청 - receiverEmail: $request.receiverEmail")

        val tokenId = authTokenRepository.save(AuthToken())
            .id
            .toString()
        val message = mailSender.createMimeMessage(request.receiverEmail, tokenId)

        mailSender.send(message)
        log.info("[AuthMailService.sendAuthenticationMail] 인증메일 발송 완료")
    }

    fun validateAuthToken(tokenId: String) {
        val token: AuthToken = authTokenRepository.findByIdOrNull(UUID.fromString(tokenId))
            ?.apply { revalidate(LocalDateTime.now()) }
            ?: run {
                log.info("[AuthService.validateAuthToken] authToken이 존재하지 않습니다 - tokenId: $tokenId")
                throw InvalidAuthException("회원가입을 위한 인증 토큰이 존재하지 않습니다")
            }

        if (token.expired) {
            log.info("[AuthService.validateAuthToken] 이미 만료된 토큰입니다 - tokenId: $tokenId")
            throw InvalidAuthException("회원가입을 위한 인증토큰이 이미 사용하였거나 기간이 만료되었습니다")
        }

        token.expire()
    }
}
