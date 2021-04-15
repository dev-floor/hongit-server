package com.devfloor.untitled.favorite.domain

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.user.domain.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 *  TODO() : 주석
 */
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
    var id: Long? = null
        protected set

    @ManyToOne
    @JoinColumn(name = "article_id")
    val article: Article = article

    @ManyToOne
    @JoinColumn(name = "user_id")
    val author: User = author

    @Column(name = "favorite_type")
    @Enumerated(value = EnumType.STRING)
    val type: FavoriteType = type
}
