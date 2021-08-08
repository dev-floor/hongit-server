package com.devfloor.hongit.api.user.application

import com.devfloor.hongit.api.common.exception.ErrorMessages.User.EXISTING_CLASS_OF
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.EXISTING_NICKNAME
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.EXISTING_USERNAME
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.INVALID_REQUEST_INFO
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.PASSWORD_VERIFICATION_MISMATCH
import com.devfloor.hongit.api.user.application.request.JoinRequest
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import com.devfloor.hongit.core.user.domain.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Slf4j
@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
) {
    fun join(request: JoinRequest): Long {
        validateJoinInfo(request).also { log.info("[AuthService.join] 회원가입 정보 유효성 검증 완료") }

        return request.toUser()
            .apply { updatePassword(passwordEncoder.encode(password)) }
            .let { userRepository.save(it).id }
            .also { log.info("[AuthService.join] 회원가입 완료 - UserId: $it") }
    }

    private fun validateJoinInfo(request: JoinRequest) {
        Assert.isTrue(request.verifyInfo(), INVALID_REQUEST_INFO)
        Assert.isTrue(request.verifyPassword(), PASSWORD_VERIFICATION_MISMATCH)
        Assert.isTrue(userRepository.existsByUsername(request.username).not(), EXISTING_USERNAME)
        Assert.isTrue(userRepository.existsByNickname(request.nickname).not(), EXISTING_NICKNAME)
        Assert.isTrue(userRepository.existsByClassOf(request.classOf).not(), EXISTING_CLASS_OF)
    }
}
