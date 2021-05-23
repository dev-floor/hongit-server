package com.devfloor.untitled.articlehashtag.domain

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.hashtag.domain.Hashtag
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 게시물과 해시태그 정보를 관리하는 연관관계 매핑 entity
 *
 * @property id 아이디
 * @property article 게시글
 * @property hashtag 해시태그
 */
@Entity
@Table(name = "article_hashtags")
class ArticleHashtag(
    article: Article,
    hashtag: Hashtag,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_hashtag_id")
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "article_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val article: Article = article

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    val hashtag: Hashtag = hashtag
}
