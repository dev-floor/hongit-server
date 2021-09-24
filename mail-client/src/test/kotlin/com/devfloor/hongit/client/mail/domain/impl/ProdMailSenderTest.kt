package com.devfloor.hongit.client.mail.domain.impl

import com.devfloor.hongit.client.mail.common.config.MailSenderConfig
import com.devfloor.hongit.client.mail.domain.spec.MailSender
import com.devfloor.hongit.client.mail.support.MockitoHelper.any
import com.devfloor.hongit.core.authtoken.AuthToken
import com.devfloor.hongit.core.authtoken.AuthTokenRepository
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

@Disabled
@ActiveProfiles(value = ["prod"])
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [MailSenderConfig::class, SpringTemplateEngine::class])
internal class ProdMailSenderTest {
    @Autowired
    private lateinit var mailSender: MailSender

    @MockBean
    private lateinit var authTokenRepository: AuthTokenRepository

    @Test
    internal fun `send - 인증메일 발송`() {
        // given
        val authToken = AuthToken()
        given(authTokenRepository.save(any())).willReturn(authToken)

        // when
        mailSender.send(mailSender.createMimeMessage("ljy3510@gmail.com", authToken.id.toString()))
    }
}
