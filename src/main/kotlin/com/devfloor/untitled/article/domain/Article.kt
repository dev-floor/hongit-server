package com.devfloor.untitled.article.domain

import com.devfloor.untitled.common.domain.BaseEntity
import com.devfloor.untitled.user.domain.User
import javax.persistence.*

@Entity
@Table(name = "article")
class Article(
    title: String?,
    anonymous: Boolean,
    content: String,
    author: User,
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
}
