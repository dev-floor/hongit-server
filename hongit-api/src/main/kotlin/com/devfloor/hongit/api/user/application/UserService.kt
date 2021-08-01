package com.devfloor.hongit.api.user.application

import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun showByUserId(userId: Long): ProfileResponse = userRepository.findByIdOrNull(userId)
        ?.let { ProfileResponse(it) }
        ?: EntityNotFoundException.notExistsId(User::class, userId)
}
