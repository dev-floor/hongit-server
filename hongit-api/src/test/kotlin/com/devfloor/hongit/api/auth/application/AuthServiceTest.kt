package com.devfloor.hongit.api.auth.application

import com.devfloor.hongit.api.auth.exception.InvalidAuthException
import com.devfloor.hongit.client.mail.domain.spec.MailSender
import com.devfloor.hongit.core.authtoken.AuthToken
import com.devfloor.hongit.core.authtoken.AuthTokenRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional
import java.util.UUID

@ExtendWith(MockitoExtension::class)
internal class AuthServiceTest {
    @InjectMocks
    private lateinit var authService: AuthService

    @Mock
    private lateinit var authTokenRepository: AuthTokenRepository

    @Mock
    private lateinit var mailSender: MailSender

    private lateinit var authToken: AuthToken

    private val tokenId = UUID.randomUUID()

    @BeforeEach
    internal fun setUp() {
        authToken = AuthToken(id = tokenId)
    }

    @Test
    internal fun `validateAuthToken - 유효한 토큰일 경우`() {
        // given
        `when`(authTokenRepository.findById(authToken.id)).thenReturn(Optional.of(authToken))

        // when
        authService.validateAuthToken(authToken.id.toString())

        // then
        assertThat(authToken.expired).isTrue
    }

    @Test
    internal fun `validateAuthToken - 존재하지 않는 토큰일 경우 예외 발생`() {
        // given
        `when`(authTokenRepository.findById(authToken.id)).thenReturn(Optional.empty())

        // when - then
        assertThatThrownBy { authService.validateAuthToken(authToken.id.toString()) }
            .isInstanceOf(InvalidAuthException::class.java)
            .hasMessage("회원가입을 위한 인증 토큰이 존재하지 않습니다")
    }

    @Test
    internal fun `validateAuthToken - 이미 사용한 토큰일 경우 예외 발생`() {
        // given
        `when`(authTokenRepository.findById(authToken.id)).thenReturn(Optional.of(authToken))
        authToken.expire()

        // when - then
        assertThatThrownBy { authService.validateAuthToken(authToken.id.toString()) }
            .isInstanceOf(InvalidAuthException::class.java)
            .hasMessage("회원가입을 위한 인증토큰이 이미 사용하였거나 기간이 만료되었습니다")
    }
}
