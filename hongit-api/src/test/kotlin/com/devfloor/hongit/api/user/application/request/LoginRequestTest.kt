package com.devfloor.hongit.api.user.application.request

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class LoginRequestTest {
    @Test
    internal fun `toString - 패스워드 암호화`() {
        // given
        val request = LoginRequest("username", "password")

        // when - then
        assertThat(request.toString()).isEqualTo("LoginRequest(username=username, password=pa******)")
            .also { println(request) }
    }
}
