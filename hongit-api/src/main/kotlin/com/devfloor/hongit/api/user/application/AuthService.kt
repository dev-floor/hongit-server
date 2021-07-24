package com.devfloor.hongit.api.user.application

import com.devfloor.hongit.api.common.exception.ErrorMessages.User.EXISTING_NICKNAME
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.EXISTING_USERNAME
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.PASSWORD_VERIFICATION_MISMATCH
import com.devfloor.hongit.api.common.exception.ErrorMessages.User.REQUEST_NOT_APPROVED
import com.devfloor.hongit.api.user.application.request.JoinRequest
import com.devfloor.hongit.core.user.domain.UserRepository
import org.springframework.util.Assert

class AuthService(
    private val userRepository: UserRepository,
) {
    fun join(request: JoinRequest): Long {
        validate(request)
    }

    private fun validate(request: JoinRequest) {
        Assert.isTrue(userRepository.existsByUsername(request.username).not(), EXISTING_USERNAME)
        Assert.isTrue(userRepository.existsByUsername(request.nickname).not(), EXISTING_NICKNAME)
        Assert.isTrue(request.verifyPassword(), PASSWORD_VERIFICATION_MISMATCH)
        Assert.isTrue(request.approved, REQUEST_NOT_APPROVED)
    }
}
