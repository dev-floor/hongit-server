package com.devfloor.hongit.core.user.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional(readOnly = true)
interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean

    fun existsByNickname(nickname: String): Boolean

    fun existsByClassOf(classOf: String): Boolean

    fun findByNickname(nickname: String): Optional<User>
}

fun UserRepository.findByNicknameOrNull(nickname: String): User? = findByNickname(nickname).orElse(null)
