package com.devfloor.untitled.favorite.domain

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.user.domain.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "favorite")
open class Favorite(
    article: Article,
    author: User,
    type: FavoriteType
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    var id: Long? = null // 얘는 왜 가변? JPA 에서 해당 값을 초기화해주기 위해
        protected set

    @Column(name = "article")
    val article: Article = article

    @Column(name = "author")
    val author: User = author

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    val type: FavoriteType = type
}