package com.devfloor.untitled.article.domain

import com.devfloor.untitled.common.domain.BaseEntity
import com.devfloor.untitled.user.domain.User
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

// TODO() : 주석

@Entity
@Table(name = "article")
class Article(
    title: String?,
    anonymous: Boolean,
    content: String,
    author: User,
    options: List<Option>
) : BaseEntity() {
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    val author: User = author

    @Column(name = "options")
    @Enumerated(value = EnumType.STRING)
    var options: List<Option> = options
        protected set
}
