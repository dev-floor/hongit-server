package com.devfloor.hongit.core.articlehashtag.domain

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.common.domain.BaseEntity
import com.devfloor.hongit.core.hashtag.domain.Hashtag
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 게시물과 해시태그의 연관관계 매핑 entity
 *
 * @property id 아이디
 * @property article 게시글
 * @property hashtag 해시태그
 */
@Entity
@Table(
    name = "article_hashtags",
    indexes = [
        Index(name = "idx_article_id", columnList = "article_id")
    ]
)
class ArticleHashtag(
    article: Article,
    hashtag: Hashtag,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_hashtag_id")
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "article_id")
    val article: Article = article

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    val hashtag: Hashtag = hashtag
}
