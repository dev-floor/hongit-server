package com.devfloor.hongit.api.security.web.context

import com.devfloor.hongit.api.security.auth.token.JwtTokenProvider
import com.devfloor.hongit.api.security.web.exception.AuthenticationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TokenSecurityInterceptor::class])
internal class TokenSecurityInterceptorTest {
    @Autowired
    private lateinit var interceptor: TokenSecurityInterceptor

    @MockBean
    private lateinit var jwtTokenProvider: JwtTokenProvider

    private lateinit var request: MockHttpServletRequest

    private lateinit var response: MockHttpServletResponse

    @BeforeEach
    internal fun setUp() {
        request = MockHttpServletRequest()
        response = MockHttpServletResponse()
    }

    @Test
    internal fun `preHandle - 로그인 토큰이 비어있으면 예외 발생`() {
        // when - then
        assertThatThrownBy { interceptor.preHandle(request, response, null) }
            .isInstanceOf(AuthenticationException::class.java)
            .hasMessage("로그인 토큰이 존재하지 않습니다.")
    }

    @Test
    internal fun `preHandle - 로그인 토큰이 유효하지 않으면 예외 발생`() {
        // given
        request.addHeader(AUTHORIZATION, TEST_AUTHORIZATION_HEADER)

        // when - then
        assertThatThrownBy { interceptor.preHandle(request, response, null) }
            .isInstanceOf(AuthenticationException::class.java)
            .hasMessage("로그인 토큰이 유효하지 않습니다.")
    }

    @Test
    internal fun `preHandle - 로그인 토큰이 유효하면 true 반환`() {
        // given
        request.addHeader(AUTHORIZATION, TEST_AUTHORIZATION_HEADER)
        `when`(jwtTokenProvider.validateToken(anyString())).thenReturn(true)

        // when - then
        assertThat(interceptor.preHandle(request, response, null)).isTrue
    }

    companion object {
        private const val TEST_AUTHORIZATION_HEADER = "BEARER secretsecretsecret"
    }
}
