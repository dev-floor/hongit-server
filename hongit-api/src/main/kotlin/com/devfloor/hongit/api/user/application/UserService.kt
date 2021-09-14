package com.devfloor.hongit.api.user.application

import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserRepository
import com.devfloor.hongit.core.user.domain.findByNicknameOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun showByNickname(nickname: String): ProfileResponse = userRepository.findByNicknameOrNull(nickname)
        ?.let { ProfileResponse(it) }
        ?: EntityNotFoundException.notExistsNickname(User::class, nickname)
}
