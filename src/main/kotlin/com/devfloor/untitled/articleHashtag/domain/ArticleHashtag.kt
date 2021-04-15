package com.devfloor.untitled.articleHashtag.domain

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.hashtag.domain.Hashtag
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "article_hashtag")
open class ArticleHashtag(
    article: Article,
    hashtag: Hashtag,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_hashtag_id")
    var id: Long? = null
        protected set

    @ManyToOne
    @JoinColumn(name = "article_id")
    val article: Article = article

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    val hashtag: Hashtag = hashtag
}
