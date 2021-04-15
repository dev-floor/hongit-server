package com.devfloor.untitled.article.domain

import com.devfloor.untitled.user.domain.User
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

// TODO() : 주석

@Entity
@Table(name = "article")
open class Article(
    title: String?,
    anonymous: Boolean,
    content: String,
    author: User,
    classNumber: Option
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    var id: Long? = null
        protected set

    @Column(name = "title")
    var title: String? = title
        protected set

    @Column(name = "anonymous")
    var anonymous: Boolean = anonymous
        protected set

    @Column(name = "content")
    var content: String = content
        protected set

    @Column(name = "author")
    val author: User = author

    @Column(name = "options")
    @Enumerated(value = EnumType.STRING)
    var option: Option = classNumber
        protected set

    @Column(name = "createdDate") // TODO() : LocalDateTime super class
    @CreatedDate
    val createdDate: LocalDateTime = LocalDateTime.MIN

    @Column(name = "updatedDate") // TODO() : LocalDateTime super class
    @CreatedDate
    var updatedDate: LocalDateTime = LocalDateTime.MIN
        protected set
}
