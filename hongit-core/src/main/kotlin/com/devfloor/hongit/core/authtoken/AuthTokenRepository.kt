package com.devfloor.hongit.core.authtoken

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional(readOnly = true)
interface AuthTokenRepository : JpaRepository<AuthToken, UUID>
