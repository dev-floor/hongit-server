package com.devfloor.hongit.api.security.auth.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.util.Date

@Component
@EnableConfigurationProperties(value = [JwtTokenProperties::class])
class JwtTokenProvider(
    private val properties: JwtTokenProperties,
) {
    fun createToken(payload: String): String {
        val claims = Jwts.claims().setSubject(payload)
        val currentDate = Date()

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(currentDate)
            .setExpiration(Date(currentDate.time + properties.expireTime))
            .signWith(SignatureAlgorithm.HS256, properties.secretKey)
            .compact()
    }

    fun extractPayload(token: String): String = extractClaims(token).subject

    private fun extractClaims(token: String): Claims = Jwts.parser()
        .setSigningKey(properties.secretKey)
        .parseClaimsJws(token)
        .body

    fun validateToken(token: String): Boolean = try {
        extractClaims(token).expiration
            .before(Date())
            .not()
    } catch (e: RuntimeException) {
        false
    }
}
