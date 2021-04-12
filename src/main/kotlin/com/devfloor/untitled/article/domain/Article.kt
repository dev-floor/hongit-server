package com.devfloor.untitled.article.domain

import com.devfloor.untitled.user.domain.User
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "article")
open class Article(
    author: User,
    title: String,
    anonymous: Boolean,
    content: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    var id: Long? = null // 얘는 왜 가변? JPA 에서 해당 값을 초기화해주기 위해
        protected set

    @Column(name = "title")
    var title: String? = title
        private set

    @Column(name = "anonymous")
    var anonymous: Boolean = anonymous
        private set

    @Column(name = "content")
    var content: String = content
        private set

    @Column(name = "author")
    val author: User = author

    @Column(name = "options")
    val options: List<String> = listOf()

    @Column(name = "createdDate")
    @CreatedDate
    var createdDate: LocalDateTime = LocalDateTime.MIN
        protected set

    @Column(name = "updatedDate")
    @CreatedDate
    var updatedDate: LocalDateTime = LocalDateTime.MIN
        protected set
}
