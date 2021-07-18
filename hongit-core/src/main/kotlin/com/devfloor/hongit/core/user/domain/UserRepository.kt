package com.devfloor.hongit.core.user.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface UserRepository : JpaRepository<User, Long>
