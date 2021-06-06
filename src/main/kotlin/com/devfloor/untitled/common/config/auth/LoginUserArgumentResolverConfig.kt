package com.devfloor.untitled.common.config.auth

import com.devfloor.untitled.user.domain.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class LoginUserArgumentResolverConfig(
    private val userRepository: UserRepository,
) {
    @Bean
    @Profile(value = ["local", "dev"])
    fun defaultLoginUserArgumentResolver() = DefaultLoginUserArgumentResolver(userRepository)

    @Bean
    @Profile(value = ["prod"])
    fun prodLoginUserArgumentResolver() = ProdLoginUserArgumentResolver(userRepository)
}
