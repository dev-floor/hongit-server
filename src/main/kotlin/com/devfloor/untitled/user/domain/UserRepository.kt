package com.devfloor.untitled.user.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

/**
 * User를 관리하는 Repository
 */
@Transactional(readOnly = true)
interface UserRepository : JpaRepository<User, Long>
