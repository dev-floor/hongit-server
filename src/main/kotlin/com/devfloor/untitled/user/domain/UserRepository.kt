package com.devfloor.untitled.user.domain

import org.springframework.data.jpa.repository.JpaRepository

/**
 * User를 관리하는 Repository
 */
interface UserRepository : JpaRepository<User, Long>
