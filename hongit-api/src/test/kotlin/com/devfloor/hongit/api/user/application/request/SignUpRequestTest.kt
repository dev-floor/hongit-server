package com.devfloor.hongit.api.user.application.request

import com.devfloor.hongit.core.user.domain.UserType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SignUpRequestTest {
    @Test
    internal fun `toString - 패스워드 암호화`() {
        // given
        val request = SignUpRequest(
            username = "username",
            nickname = "nickname",
            password = "password",
            checkedPassword = "password",
            email = "email@g.hongik.ac.kr",
            type = UserType.STUDENT,
            classOf = "C000000",
            approved = true
        )

        // when - then
        assertThat(request.toString())
            .isEqualTo(
                "SignUpRequest(" +
                    "username=username, " +
                    "nickname=nickname, " +
                    "password=pa******, " +
                    "checkedPassword=pa******, " +
                    "email=email@g.hongik.ac.kr, " +
                    "type=STUDENT, " +
                    "classOf=C000000, " +
                    "approved=true" +
                    ")"
            )
            .also { println(request) }
    }
}
