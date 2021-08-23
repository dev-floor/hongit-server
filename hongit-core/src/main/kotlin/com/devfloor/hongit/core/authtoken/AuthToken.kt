package com.devfloor.hongit.core.authtoken

import com.devfloor.hongit.core.common.domain.BaseEntity
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

/**
 * 이메일 인증 시 사용되는 토큰을 관리하는 entity
 *
 * @property id 아이디
 * @property userId 토큰 대상 유저 ID
 * @property expirationTime 토큰 만료 시간
 * @property expired 토큰 만료 상태
 */
@Entity
@Table(name = "auth_tokens")
class AuthToken(
    userId: Long,
    expirationTime: LocalDateTime,
    expired: Boolean = false,
    id: UUID = UUID.randomUUID(),
) : BaseEntity() {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    val id: UUID = id

    @Column(name = "user_id")
    val userId: Long = userId

    @Column(name = "expiration_time")
    val expirationTime: LocalDateTime = expirationTime

    @Column(name = "expired")
    var expired: Boolean = expired

    constructor(userId: Long) : this(
        userId = userId,
        expirationTime = LocalDateTime.now().plusDays(TOKEN_EXPIRATION_DATE),
    )

    fun useToken() {
        this.expired = true
    }

    companion object {
        private const val TOKEN_EXPIRATION_DATE: Long = 1 // 토큰 만료 기간은 생성 후 하루
    }
}
