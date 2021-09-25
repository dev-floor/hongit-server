package com.devfloor.hongit.api.security.web.resolver.impl

import com.devfloor.hongit.api.security.web.resolver.spec.LoginUserArgumentResolver
import com.devfloor.hongit.core.user.domain.UserRepository
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import javax.persistence.EntityNotFoundException

class DefaultLoginUserArgumentResolver(
    private val userRepository: UserRepository,
) : LoginUserArgumentResolver {
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val users = userRepository.findAll()

        if (users.isEmpty()) throw EntityNotFoundException("사용자가 존재하지 않습니다.")
        return users.first()
    }
}
