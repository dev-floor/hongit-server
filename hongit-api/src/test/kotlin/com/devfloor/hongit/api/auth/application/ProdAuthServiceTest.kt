package com.devfloor.hongit.api.auth.application

import com.devfloor.hongit.api.auth.application.request.AuthMailRequest
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.client.mail.common.config.MailSenderConfig
import com.devfloor.hongit.client.mail.domain.spec.MailSender
import com.devfloor.hongit.core.authtoken.AuthToken
import com.devfloor.hongit.core.authtoken.AuthTokenRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.thymeleaf.spring5.SpringTemplateEngine

/**
 * 실제 메시지 전송 여부를 테스트하기 위한 테스트 코드 (profile = prod)
 * 테스트 시 @Disable 어노테이션 주석처리 후 실행
 */
@Disabled
@ActiveProfiles(value = ["prod"])
@ExtendWith(SpringExtension::class)
@SpringBootTest(
    classes = [AuthService::class, MailSenderConfig::class, SpringTemplateEngine::class],
    properties = ["mail.sender", "hongit"]
)
internal class ProdAuthServiceTest {
    private lateinit var service: AuthService

    @Autowired
    private lateinit var mailSender: MailSender

    @MockBean
    private lateinit var authTokenRepository: AuthTokenRepository

    @BeforeEach
    internal fun setUp() {
        service = AuthService(authTokenRepository, mailSender)
    }

    @Test
    internal fun `sendAuthenticationMail - 인증 토큰에 따른 메일 발송`() {
        // given
        given(authTokenRepository.save(any())).willReturn(AuthToken())

        // when
        service.sendAuthenticationMail(AuthMailRequest("ljy3510@gmail.com"))
    }
}
