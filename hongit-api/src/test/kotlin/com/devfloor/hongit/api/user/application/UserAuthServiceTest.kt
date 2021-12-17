package com.devfloor.hongit.api.user.application

import com.devfloor.hongit.api.common.exception.UnprocessableEntityException
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.core.user.domain.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class UserAuthServiceTest {
    @InjectMocks
    private lateinit var userAuthService: UserAuthService

    @Mock
    private lateinit var userRepository: UserRepository

    @Test
    internal fun `validateUsername - 유효한 username인 경우 검증에 성공`() {
        // given
        `when`(userRepository.existsByUsername(any())).thenReturn(false)

        // when
        val result = userAuthService.validateUsername("username")

        // then
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    internal fun `validateUsername - 유효하지 않은 username인 경우 검증에 실패`() {
        // given
        `when`(userRepository.existsByUsername(any())).thenReturn(true)

        // when - then
        assertThatThrownBy { userAuthService.validateUsername("username") }
            .isInstanceOf(UnprocessableEntityException::class.java)
            .hasMessage("username에 해당하는 User이(가) 이미 존재합니다 - username: username")
    }

    @Test
    internal fun `validateNickname - 유효한 nickname인 경우 검증에 성공`() {
        // given
        `when`(userRepository.existsByNickname(any())).thenReturn(false)

        // when
        val result = userAuthService.validateNickname("nickname")

        // then
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    internal fun `validateUsername - 유효하지 않은 nickname인 경우 검증에 실패`() {
        // given
        `when`(userRepository.existsByNickname(any())).thenReturn(true)

        // when - then
        assertThatThrownBy { userAuthService.validateNickname("nickname") }
            .isInstanceOf(UnprocessableEntityException::class.java)
            .hasMessage("nickname에 해당하는 User이(가) 이미 존재합니다 - nickname: nickname")
    }

    @Test
    internal fun `validateClassOf - 유효한 학번인 경우 검증에 성공`() {
        // given
        `when`(userRepository.existsByClassOf(any())).thenReturn(false)

        // when
        val result = userAuthService.validateClassOf("b211111")

        // then
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    internal fun `validateUsername - 유효하지 않은 학번인 경우 검증에 실패`() {
        // given
        `when`(userRepository.existsByClassOf(any())).thenReturn(true)

        // when - then
        assertThatThrownBy { userAuthService.validateClassOf("b211111") }
            .isInstanceOf(UnprocessableEntityException::class.java)
            .hasMessage("classOf에 해당하는 User이(가) 이미 존재합니다 - classOf: b211111")
    }
}
