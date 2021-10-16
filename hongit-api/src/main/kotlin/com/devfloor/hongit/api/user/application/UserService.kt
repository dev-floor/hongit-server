package com.devfloor.hongit.api.user.application

import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.common.exception.ErrorMessages
import com.devfloor.hongit.api.user.application.request.SignUpRequest
import com.devfloor.hongit.api.user.application.request.UserModifyRequest
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserRepository
import com.devfloor.hongit.core.user.domain.UserType
import com.devfloor.hongit.core.user.domain.findByNicknameOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
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
        Assert.isTrue(request.verifyPassword(), ErrorMessages.User.PASSWORD_VERIFICATION_MISMATCH)
        Assert.isTrue(userRepository.existsByUsername(request.username).not(), ErrorMessages.User.EXISTING_USERNAME)
        Assert.isTrue(userRepository.existsByNickname(request.nickname).not(), ErrorMessages.User.EXISTING_NICKNAME)
        Assert.isTrue(userRepository.existsByClassOf(request.classOf).not(), ErrorMessages.User.EXISTING_CLASS_OF)

        log.info("[AuthService.join] 회원가입 정보 유효성 검증 완료")
    }

    fun showByNickname(nickname: String): ProfileResponse = userRepository.findByNicknameOrNull(nickname)
        ?.let { ProfileResponse(it) }
        ?: EntityNotFoundException.notExistsNickname(User::class, nickname)

    fun modifyUser(loginUser: User, request: UserModifyRequest) {
        userRepository.findByNicknameOrNull(loginUser.nickname)
            ?.apply {
                if (checkNickname(loginUser.nickname, request.nickname)) {
                    modifyUser(
                        request.nickname,
                        UserType.valueOf(request.userType),
                        request.image,
                        request.github,
                        request.blog,
                        request.description
                    )
                } else throw IllegalArgumentException(ErrorMessages.User.EXISTING_NICKNAME)
            }
            ?: EntityNotFoundException.notExistsNickname(User::class, loginUser.nickname)
    }

    private fun checkNickname(nickname: String, requestNickname: String): Boolean {
        return !userRepository.existsByNickname(requestNickname) && nickname != (requestNickname)
    }
}
