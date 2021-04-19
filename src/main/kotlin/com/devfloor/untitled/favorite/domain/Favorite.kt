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
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 좋아요, 나도 궁금해요, 스크랩 정보를 관리하는 entity
 *
 * @property id 아이디
 * @property article 게시글
 * @property user 좋아요를 누른 유저
 * @property type 좋아요 종류
 */
@Entity
@Table(name = "favorite")
class Favorite(
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
    val user: User = author

    @Column(name = "favorite_type")
    @Enumerated(value = EnumType.STRING)
    val type: FavoriteType = type
}
