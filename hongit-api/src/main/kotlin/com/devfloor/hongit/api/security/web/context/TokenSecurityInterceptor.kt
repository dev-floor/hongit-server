package com.devfloor.hongit.api.security.web.context

import com.devfloor.hongit.api.security.auth.authentication.AuthorizationExtractor
import com.devfloor.hongit.api.security.auth.token.JwtTokenProvider
import com.devfloor.hongit.api.security.utils.USERNAME_ATTRIBUTE
import com.devfloor.hongit.api.security.web.AuthorizationType
import com.devfloor.hongit.api.security.web.exception.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenSecurityInterceptor(
    private val jwtTokenProvider: JwtTokenProvider,
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any?): Boolean {
        val credentials = AuthorizationExtractor.extract(request, AuthorizationType.BEARER)

        if (!StringUtils.hasText(credentials)) throw AuthenticationException("로그인 토큰이 존재하지 않습니다.")

        if (!jwtTokenProvider.isValidToken(credentials)) throw AuthenticationException("로그인 토큰이 유효하지 않습니다.")

        request.setAttribute(USERNAME_ATTRIBUTE, jwtTokenProvider.extractSubject(credentials))
        return true
    }
}
