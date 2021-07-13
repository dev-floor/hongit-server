package com.devfloor.hongit.core.user.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

/**
 * 회원을 관리하는 Repository
 */
@Transactional(readOnly = true)
interface UserRepository : JpaRepository<User, Long>
