package com.devfloor.hongit.core.user.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class EmailTest {
    @Test
    internal fun `from - email 주소를 입력받아 Email 객체 생성`() {
        // given
        val email = "hello@g.hongik.ac.kr"

        // when
        val actual = Email.from(email)

        // then
        assertThat(actual).isInstanceOf(Email::class.java)
        assertThat(actual.name).isEqualTo("hello")
        assertThat(actual.domain).isEqualTo("g.hongik.ac.kr")
    }

    @ParameterizedTest
    @ValueSource(strings = ["hello", "hello@g.hongik@ac.kr"])
    internal fun `from - email 주소가 유효하지 않을 경우 exception 발생`(value: String) {
        // when - then
        assertThatThrownBy { Email.from(value) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("유효하지 않은 email 주소입니다.")
    }

    @ParameterizedTest
    @ValueSource(strings = ["gmail.com", "naver.com"])
    internal fun `validate - email의 domain 주소가 유효하지 않을 경우 exception 발생`(domain: String) {
        // when - then
        assertThatThrownBy { Email("hello", domain) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("유효하지 않은 도메인입니다.")
    }

    @Test
    internal fun `toEmailString - email의 name과 domain을 연결한 형태로 반환`() {
        // given
        val requestEmail = "hello@g.hongik.ac.kr"
        val email = Email.from(requestEmail)

        // when
        val actual = email.toEmailString()

        // then
        assertThat(actual).isEqualTo(requestEmail)
    }
}
