package com.devfloor.hongit.api.user.application

import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.common.exception.ErrorMessages
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.PASSWORD_VERIFICATION_MISMATCH
import com.devfloor.hongit.api.security.auth.token.JwtTokenProvider
import com.devfloor.hongit.api.security.web.AuthorizationType
import com.devfloor.hongit.api.security.web.exception.AuthenticationException
import com.devfloor.hongit.api.user.application.request.LoginRequest
import com.devfloor.hongit.api.user.application.request.PasswordModifyRequest
import com.devfloor.hongit.api.user.application.request.SignUpRequest
import com.devfloor.hongit.api.user.application.request.UserModifyRequest
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import com.devfloor.hongit.api.user.application.response.TokenResponse
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserRepository
import com.devfloor.hongit.core.user.domain.UserType
import com.devfloor.hongit.core.user.domain.findByNicknameOrNull
import com.devfloor.hongit.core.user.domain.findByUsernameOrNull
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import javax.transaction.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    fun signUp(request: SignUpRequest): Long {
        validateJoinInfo(request)

        return request.toUser()
            .apply { encodePassword(passwordEncoder) }
            .let { userRepository.save(it) }
            .also { log.info("[AuthService.join] 회원가입 완료 - UserId: ${it.id}") }
            .id
    }

    private fun validateJoinInfo(request: SignUpRequest) {
        Assert.isTrue(request.verifyInfo(), ErrorMessages.User.INVALID_REQUEST_INFO)
        Assert.isTrue(request.verifyPassword(), ErrorMessages.User.PASSWORD_AND_CHECKED_PASSWORD_VERIFICATION_MISMATCH)
        Assert.isTrue(userRepository.existsByUsername(request.username).not(), ErrorMessages.User.EXISTING_USERNAME)
        Assert.isTrue(userRepository.existsByNickname(request.nickname).not(), ErrorMessages.User.EXISTING_NICKNAME)
        Assert.isTrue(userRepository.existsByClassOf(request.classOf).not(), ErrorMessages.User.EXISTING_CLASS_OF)

        log.info("[AuthService.join] 회원가입 정보 유효성 검증 완료")
    }

    fun showByNickname(nickname: String): ProfileResponse = userRepository.findByNicknameOrNull(nickname)
        ?.let { ProfileResponse(it) }
        ?: EntityNotFoundException.notExistsField(User::class, "nickname", nickname)

    fun login(request: LoginRequest): TokenResponse {
        val user = userRepository.findByUsernameOrNull(request.username)
            ?: EntityNotFoundException.notExistsField(User::class, "username", request.username)

        validatePassword(user, request.password)

        return TokenResponse(jwtTokenProvider.createToken(request.username), AuthorizationType.BEARER)
    }

    @Transactional
    fun modifyUser(loginUser: User, request: UserModifyRequest) {
        userRepository.findByNicknameOrNull(loginUser.nickname)
            ?.apply {
                if (validateNickname(loginUser.nickname, request.nickname)) {
                    modifyUser(
                        request.nickname,
                        UserType.valueOf(request.userType),
                        request.image,
                        request.github,
                        request.blog,
                        request.description
                    )
                }
            }
            ?: throw IllegalArgumentException(ErrorMessages.User.NOT_EXISTING_USERNAME)
    }

    private fun validateNickname(nickname: String, requestNickname: String): Boolean {
        if (!isExistNickname(requestNickname) && isNotEqualNickname(nickname, requestNickname)) {
            return true
        } else throw IllegalArgumentException(ErrorMessages.User.EXISTING_NICKNAME)
    }

    private fun isExistNickname(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }

    private fun isNotEqualNickname(nickname: String, requestNickname: String): Boolean {
        return nickname != requestNickname
    }

    @Transactional
    fun modifyPassword(request: PasswordModifyRequest, id: Long) {
        if (!request.matchesPassword()) {
            throw AuthenticationException(ErrorMessages.User.PASSWORD_AND_CHECKED_PASSWORD_VERIFICATION_MISMATCH)
        }

        val user = userRepository.findByIdOrNull(id) ?: EntityNotFoundException.notExistsId(User::class, id)
        validatePassword(user, request.oldPassword)

        user.apply { modifyPassword(request.newPassword) }
            .apply { encodePassword(passwordEncoder) }
    }

    @Transactional
    fun destroy(id: Long, password: String) = userRepository.findByIdOrNull(id)
        ?.apply { validatePassword(this, password) }
        ?.let { userRepository.delete(it) }
        ?: EntityNotFoundException.notExistsId(User::class, id)

    private fun validatePassword(user: User, password: String) {
        if (!user.matchesPassword(passwordEncoder, password)) {
            throw AuthenticationException(PASSWORD_VERIFICATION_MISMATCH)
        }
    }
}
