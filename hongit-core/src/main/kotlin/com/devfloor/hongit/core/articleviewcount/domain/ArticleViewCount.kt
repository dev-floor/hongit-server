package com.devfloor.hongit.core.articleviewcount.domain

import com.devfloor.hongit.core.article.domain.Article
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "article_view_count")
class ArticleViewCount(
    article: Article,
    count: Long = 1,
    id: Long = 0,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_view_count_id")
    val id: Long = id

    @OneToOne
    @JoinColumn(name = "article_id")
    val article: Article = article

    @Column(name = "count")
    var count: Long = count
        protected set

    fun withId(id: Long): ArticleViewCount = ArticleViewCount(article, count, id)

    fun increase() = this.count++
}
