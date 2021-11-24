package com.devfloor.hongit.api.user.application

import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.EXISTING_CLASS_OF
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.EXISTING_NICKNAME
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.EXISTING_USERNAME
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.INVALID_REQUEST_INFO
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.PASSWORD_AND_CHECKED_PASSWORD_VERIFICATION_MISMATCH
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.PASSWORD_VERIFICATION_MISMATCH
import com.devfloor.hongit.api.security.auth.token.JwtTokenProvider
import com.devfloor.hongit.api.security.web.AuthorizationType
import com.devfloor.hongit.api.security.web.exception.AuthenticationException
import com.devfloor.hongit.api.user.application.request.LoginRequest
import com.devfloor.hongit.api.user.application.request.PasswordModifyRequest
import com.devfloor.hongit.api.user.application.request.SignUpRequest
import com.devfloor.hongit.api.user.application.response.TokenResponse
import com.devfloor.hongit.core.user.domain.Email
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserRepository
import com.devfloor.hongit.core.user.domain.UserType
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doNothing
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.Optional

@ExtendWith(MockitoExtension::class)
internal class UserServiceTest {
    @InjectMocks
    private lateinit var userService: UserService

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    private lateinit var user: User

    private lateinit var signUpRequest: SignUpRequest

    private lateinit var loginRequest: LoginRequest

    private lateinit var passwordModifyRequest: PasswordModifyRequest

    @BeforeEach
    internal fun setUp() {
        user = User(
            id = 0,
            username = "username",
            password = "password",
            nickname = "nickname",
            email = Email.from("email@g.hongik.ac.kr"),
            type = UserType.GRADUATE,
            classOf = "B411158",
            image = "https://image.com",
            github = "github",
            blog = "blog",
            description = "test user description",
        )
        signUpRequest = SignUpRequest(
            username = "username",
            nickname = "nickname",
            password = "password",
            checkedPassword = "password",
            email = "b211111@mail.hongik.ac.kr",
            type = UserType.STUDENT,
            classOf = "b211111",
            approved = true,
        )
        loginRequest = LoginRequest("username", "password")
        passwordModifyRequest = PasswordModifyRequest(
            oldPassword = "password",
            newPassword = "newPassword",
            checkedNewPassword = "newPassword"
        )
    }

    @Test
    internal fun `signUp - 유저 회원가입에 성공하는 경우 id 반환`() {
        // given
        `when`(userRepository.existsByUsername(any())).thenReturn(false)
        `when`(userRepository.existsByNickname(any())).thenReturn(false)
        `when`(userRepository.existsByClassOf(any())).thenReturn(false)
        `when`(passwordEncoder.encode(any())).thenReturn("encoded")
        `when`(userRepository.save(any())).thenReturn(user)

        // when
        val result = userService.signUp(signUpRequest)

        // then
        assertThat(result).isEqualTo(user.id)
    }

    @Test
    internal fun `signUp - 회원가입 요청 정보가 유효하지 않은 경우 예외 발생`() {
        // given - when - then
        val invalidSignUpRequest = SignUpRequest(
            username = "username",
            nickname = "nickname",
            password = "password",
            checkedPassword = "password",
            email = "b211111@mail.hongik.ac.kr",
            type = UserType.STUDENT,
            classOf = "b211111",
            approved = false,
        )
        assertThatThrownBy { userService.signUp(invalidSignUpRequest) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(INVALID_REQUEST_INFO)
    }

    @Test
    internal fun `signUp - 비밀번호와 비밀번호 확인이 일치하지 않아 검증에 실패하는 경우 예외 발생`() {
        // given - when - then
        val passwordMisMatchingSignUpRequest = SignUpRequest(
            username = "username",
            nickname = "nickname",
            password = "password",
            checkedPassword = "wrong password",
            email = "b211111@mail.hongik.ac.kr",
            type = UserType.STUDENT,
            classOf = "b211111",
            approved = true,
        )
        assertThatThrownBy { userService.signUp(passwordMisMatchingSignUpRequest) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(PASSWORD_AND_CHECKED_PASSWORD_VERIFICATION_MISMATCH)
    }

    @Test
    internal fun `signUp - 아이디가 이미 존재하는 경우 예외 발생`() {
        // given
        `when`(userRepository.existsByUsername(any())).thenReturn(true)

        // when - then
        assertThatThrownBy { userService.signUp(signUpRequest) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(EXISTING_USERNAME)
    }

    @Test
    internal fun `signUp - 닉네임이 이미 존재하는 경우 예외 발생`() {
        // given
        `when`(userRepository.existsByUsername(any())).thenReturn(false)
        `when`(userRepository.existsByNickname(any())).thenReturn(true)

        // when - then
        assertThatThrownBy { userService.signUp(signUpRequest) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(EXISTING_NICKNAME)
    }

    @Test
    internal fun `signUp - 학번이 이미 존재하는 경우 예외 발생`() {
        // given
        `when`(userRepository.existsByUsername(any())).thenReturn(false)
        `when`(userRepository.existsByNickname(any())).thenReturn(false)
        `when`(userRepository.existsByClassOf(any())).thenReturn(true)

        // when - then
        assertThatThrownBy { userService.signUp(signUpRequest) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(EXISTING_CLASS_OF)
    }

    @Test
    internal fun `showByNickname - 닉네임으로 회원 조회에 성공하고 profile response 반환`() {
        // given
        `when`(userRepository.findByNickname(any())).thenReturn(Optional.of(user))

        // when
        val result = userService.showByNickname("nickname")

        // then
        assertThat(result).isEqualTo(ProfileResponse(user))
    }

    @Test
    internal fun `showByNickname - nickname에 해당하는 회원이 없을 경우 예외 발생`() {
        // given
        `when`(userRepository.findByNickname(any())).thenReturn(Optional.empty())

        // when - then
        assertThatThrownBy { userService.showByNickname("nickname") }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage("nickname에 해당하는 User이(가) 존재하지 않습니다 - nickname: nickname")
    }

    @Test
    internal fun `login - username으로 조회, 패스워드 확인 후 토큰 발행`() {
        // given
        `when`(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user))
        `when`(passwordEncoder.matches(anyString(), anyString())).thenReturn(true)
        `when`(jwtTokenProvider.createToken(anyString())).thenReturn("token")

        // when
        val actual = userService.login(loginRequest)

        // then
        assertThat(actual).isEqualTo(TokenResponse("token", AuthorizationType.BEARER))
    }

    @Test
    internal fun `login - username에 해당하는 회원이 없을 경우 예외 발생`() {
        // given
        `when`(userRepository.findByUsername(anyString())).thenReturn(Optional.empty())

        // when - then
        assertThatThrownBy { userService.login(loginRequest) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage("username에 해당하는 User이(가) 존재하지 않습니다 - username: username")
    }

    @Test
    internal fun `login - 비밀번호 인증에 실패할 경우 예외 발생`() {
        // given
        `when`(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user))
        `when`(passwordEncoder.matches(anyString(), anyString())).thenReturn(false)

        // when - then
        assertThatThrownBy { userService.login(loginRequest) }
            .isInstanceOf(AuthenticationException::class.java)
            .hasMessage(PASSWORD_VERIFICATION_MISMATCH)
    }

    @Test
    internal fun `modifyPassword - 비밀번호 변경 성공`() {
        // given
        `when`(userRepository.findById(anyLong())).thenReturn(Optional.of(user))
        `when`(passwordEncoder.matches(anyString(), anyString())).thenReturn(true)
        `when`(passwordEncoder.encode(anyString())).thenReturn(passwordModifyRequest.newPassword)

        // when
        val result = userService.modifyPassword(passwordModifyRequest, 0)

        // then
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    internal fun `modifyPassword - 비밀번호와 비밀번호 확인 불일치할 경우 예외 발생`() {
        // given
        val wrongRequest = PasswordModifyRequest(user.password, "password", "wrongCheckedPassword")
        val errorMessage = PASSWORD_AND_CHECKED_PASSWORD_VERIFICATION_MISMATCH

        // when - then
        assertThatThrownBy { userService.modifyPassword(wrongRequest, 0) }
            .isInstanceOf(AuthenticationException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `modifyPassword - id에 해당하는 Article 객체가 존재하지 않는 경우 예외 발생`() {
        // given
        `when`(userRepository.findById(anyLong())).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 User이(가) 존재하지 않습니다 - UserId: 0"

        // when - then
        assertThatThrownBy { userService.modifyPassword(passwordModifyRequest, 0) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `modifyPassword - 비밀번호 인증에 실패하는 경우 예외 발생`() {
        // given
        `when`(userRepository.findById(anyLong())).thenReturn(Optional.of(user))
        `when`(passwordEncoder.matches(anyString(), anyString())).thenReturn(false)
        val errorMessage = PASSWORD_VERIFICATION_MISMATCH

        // when - then
        assertThatThrownBy { userService.modifyPassword(passwordModifyRequest, 0) }
            .isInstanceOf(AuthenticationException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `destroy - 회원 탈퇴 성공`() {
        // given
        `when`(userRepository.findById(anyLong())).thenReturn(Optional.of(user))
        `when`(passwordEncoder.matches(anyString(), anyString())).thenReturn(true)
        doNothing().`when`(userRepository).delete(any())

        // when
        val result = userService.destroy(0, user.password)

        // then
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    internal fun `destroy - id에 해당하는 Article 객체가 존재하지 않는 경우 예외 발생`() {
        `when`(userRepository.findById(anyLong())).thenReturn(Optional.empty())
        val errorMessage = "id에 해당하는 User이(가) 존재하지 않습니다 - UserId: 0"

        assertThatThrownBy { userService.destroy(0, user.password) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage(errorMessage)
    }

    @Test
    internal fun `destroy - 비밀번호 인증에 실패하는 경우 예외 발생`() {
        `when`(userRepository.findById(anyLong())).thenReturn(Optional.of(user))
        `when`(passwordEncoder.matches(anyString(), anyString())).thenReturn(false)
        val errorMessage = PASSWORD_VERIFICATION_MISMATCH

        assertThatThrownBy { userService.destroy(0, user.password) }
            .isInstanceOf(AuthenticationException::class.java)
            .hasMessage(errorMessage)
    }
}
