package com.devfloor.hongit.core.user.domain

import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * Email 정보를 관리하는 embedded 객체
 *
 * @property name email 사용자 이름
 * @property domain email 도메인
 */
@Embeddable
class Email(
    name: String,
    domain: String,
) {
    @Column(name = "name")
    val name: String = name

    @Column(name = "domain")
    val domain: String = domain

    init {
        validate(domain)
    }

    private fun validate(domain: String) {
        if (!VALID_DOMAINS.contains(domain)) throw IllegalArgumentException("유효하지 않은 도메인입니다.")
    }

    companion object {
        private const val AT_SIGN = "@"
        private val VALID_DOMAINS = listOf("mail.hongik.ac.kr", "g.hongik.ac.kr")

        fun from(email: String): Email {
            val splits = email.split(AT_SIGN)
                .also { if (it.size != 2) throw IllegalArgumentException("유효하지 않은 email 주소입니다.") }
            return Email(splits[0], splits[1])
        }
    }
}
