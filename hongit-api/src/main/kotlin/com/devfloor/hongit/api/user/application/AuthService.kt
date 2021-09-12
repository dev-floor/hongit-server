package com.devfloor.hongit.api.user.application

import com.devfloor.hongit.api.common.exception.UnprocessableEntityException
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
) {
    fun validateUsername(username: String) {
        if (userRepository.existsByUsername(username)) {
            UnprocessableEntityException.throwAlreadyExists(User::class, "username", username)
        }
    }

    fun validateNickname(nickname: String) {
        if (userRepository.existsByNickname(nickname)) {
            UnprocessableEntityException.throwAlreadyExists(User::class, "nickname", nickname)
        }
    }

    fun validateClassOf(classOf: String) {
        if (userRepository.existsByClassOf(classOf)) {
            UnprocessableEntityException.throwAlreadyExists(User::class, "classOf", classOf)
        }
    }
}
