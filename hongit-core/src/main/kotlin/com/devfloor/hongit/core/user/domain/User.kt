package com.devfloor.hongit.core.user.domain

import com.devfloor.hongit.core.common.domain.BaseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Table

/**
 * 회원 정보를 관리하는 entity
 *
 * @property id 아이디
 * @property username 계정 아이디
 * @property password 비밀번호
 * @property nickname 닉네임
 * @property email 이메일
 * @property type 유형
 * @property classOf 학번
 * @property image 프로필 사진
 * @property github 깃허브 주소
 * @property blog 블로그 주소
 * @property description 추가 설명
 */
@Entity
@Table(
    name = "users",
    indexes = [
        Index(name = "idx_username", columnList = "username"),
        Index(name = "idx_nickname", columnList = "nickname")
    ],
)
class User(
    username: String,
    password: String,
    nickname: String,
    email: Email,
    type: UserType,
    classOf: String,
    image: String? = null,
    github: String? = null,
    blog: String? = null,
    description: String? = null,
    id: Long = 0,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long = id

    @Column(name = "username")
    val username: String = username

    @Column(name = "password")
    var password: String = password
        protected set

    @Column(name = "nickname")
    var nickname: String = nickname
        protected set

    @Embedded
    @Column(name = "email")
    val email: Email = email

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_type")
    var type: UserType = type
        protected set

    @Column(name = "class_of")
    var classOf: String = classOf
        protected set

    @Column(name = "image")
    var image: String? = image
        protected set

    @Column(name = "github")
    var github: String? = github
        protected set

    @Column(name = "blog")
    var blog: String? = blog
        protected set

    @Column(name = "description")
    var description: String? = description
        protected set

    fun modifyProfile(image: String?, github: String?, blog: String?, description: String?) {
        this.image = image
        this.github = github
        this.blog = blog
        this.description = description
    }

    fun modifyUser(
        nickname: String,
        userType: UserType,
        image: String?,
        github: String?,
        blog: String?,
        description: String?
    ) {
        this.nickname = nickname
        this.type = userType
        this.image = image
        this.github = github
        this.blog = blog
        this.description = description
    }

    fun encodePassword(passwordEncoder: PasswordEncoder) {
        val encodedPassword = passwordEncoder.encode(this.password)
        this.password = encodedPassword
    }

    fun hasSameId(user: User): Boolean {
        return this.id == user.id
    }

    fun isSameNickname(nickname: String): Boolean {
        return this.nickname == nickname
    }
}
