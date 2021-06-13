package com.devfloor.untitled.common.config.auth

import com.devfloor.untitled.common.exception.EntityNotFoundException
import com.devfloor.untitled.user.domain.UserRepository
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer

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
