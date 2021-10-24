package com.devfloor.hongit.api.security.web.resolver.impl

import com.devfloor.hongit.api.security.utils.USERNAME_ATTRIBUTE
import com.devfloor.hongit.api.security.web.exception.AuthenticationException
import com.devfloor.hongit.api.security.web.resolver.spec.LoginUserArgumentResolver
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import com.devfloor.hongit.core.user.domain.UserRepository
import com.devfloor.hongit.core.user.domain.findByUsernameOrNull
import org.springframework.core.MethodParameter
import org.springframework.util.StringUtils
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.method.support.ModelAndViewContainer

@Slf4j
class ProdLoginUserArgumentResolver(
    private val userRepository: UserRepository,
) : LoginUserArgumentResolver {
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? = try {
        val username = webRequest.getAttribute(USERNAME_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST) as String

        if (!StringUtils.hasText(username)) throw AuthenticationException("인증된 사용자가 존재하지 않습니다.")

        userRepository.findByUsernameOrNull(username)
            ?: throw AuthenticationException("Username이 일치하는 사용자가 존재하지 않습니다 - username: $username")
                .also { log.error("Username이 일치하는 사용자가 존재하지 않습니다 - username: $username") }
    } catch (e: AuthenticationException) {
        throw AuthenticationException(e.message)
    } catch (e: Exception) {
        throw AuthenticationException("비정상적인 로그인입니다.")
    }
}
