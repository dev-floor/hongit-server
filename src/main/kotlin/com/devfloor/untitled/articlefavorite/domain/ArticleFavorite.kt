package com.devfloor.untitled.articlefavorite.domain

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.common.domain.BaseEntity
import com.devfloor.untitled.user.domain.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 게시글에 대한 좋아요를 관리하는 entity
 *
 * @property id 아이디
 * @property article 게시글
 * @property user 좋아요를 누른 유저
 * @property type 좋아요 종류
 */
@Entity
@Table(
    name = "article_favorites",
    indexes = [
        Index(name = "idx_article_id", columnList = "article_id")
    ]
)
class ArticleFavorite(
    article: Article,
    user: User,
    type: ArticleFavoriteType,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "article_id")
    val article: Article = article

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User = user

    @Column(name = "favorite_type")
    @Enumerated(value = EnumType.STRING)
    val type: ArticleFavoriteType = type

    fun matchType(type: ArticleFavoriteType): Boolean = this.type == type
}
