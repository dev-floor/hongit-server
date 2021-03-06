package com.devfloor.hongit.api.security.auth.authentication

import com.devfloor.hongit.api.security.utils.ACCESS_TOKEN_TYPE
import com.devfloor.hongit.api.security.web.AuthorizationType
import org.apache.logging.log4j.util.Strings
import org.springframework.http.HttpHeaders.AUTHORIZATION
import javax.servlet.http.HttpServletRequest

object AuthorizationExtractor {
    fun extract(request: HttpServletRequest, type: AuthorizationType): String {
        val authorizationTypeLength = type.toLowerCase().length
        val headers = request.getHeaders(AUTHORIZATION)

        while (headers.hasMoreElements()) {
            val nextElement = headers.nextElement()

            if (nextElement.lowercase().startsWith(type.toLowerCase())) {
                val authHeaderValue = nextElement.substring(authorizationTypeLength).trim()
                val commaIndex = authHeaderValue.indexOf(',')

                request.setAttribute(ACCESS_TOKEN_TYPE, nextElement.substring(0, authorizationTypeLength).trim())
                return if (commaIndex > 0) authHeaderValue.substring(0, commaIndex) else authHeaderValue
            }
        }
        return Strings.EMPTY
    }
}
