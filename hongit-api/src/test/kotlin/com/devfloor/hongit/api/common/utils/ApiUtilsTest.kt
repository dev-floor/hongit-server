package com.devfloor.hongit.api.common.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ApiUtilsTest {
    @Test
    internal fun `mask - 특정 문자를 최초 2자리 이후로 마스킹`() {
        // given
        val text = "secret"

        // when
        val actual = ApiUtils.mask(text)

        // then
        assertThat(actual).isEqualTo("se****")
    }
}
