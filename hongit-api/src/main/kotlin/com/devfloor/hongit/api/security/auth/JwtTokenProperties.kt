package com.devfloor.hongit.api.security.auth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "security.jwt.token")
data class JwtTokenProperties(
    val secretKey: String,
    val expireTime: Long,
)
