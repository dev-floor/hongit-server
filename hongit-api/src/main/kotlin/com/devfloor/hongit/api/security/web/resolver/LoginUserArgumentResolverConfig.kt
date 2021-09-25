package com.devfloor.hongit.api.security.web.resolver

import com.devfloor.hongit.api.security.web.resolver.impl.DefaultLoginUserArgumentResolver
import com.devfloor.hongit.api.security.web.resolver.impl.ProdLoginUserArgumentResolver
import com.devfloor.hongit.core.user.domain.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class LoginUserArgumentResolverConfig(
    private val userRepository: UserRepository,
) {
    @Bean
    @Profile(value = ["!prod"])
    fun defaultLoginUserArgumentResolver() = DefaultLoginUserArgumentResolver(userRepository)

    @Bean
    @Profile(value = ["prod"])
    fun prodLoginUserArgumentResolver() = ProdLoginUserArgumentResolver(userRepository)
}
