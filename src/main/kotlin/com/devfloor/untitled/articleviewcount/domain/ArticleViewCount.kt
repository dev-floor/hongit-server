package com.devfloor.untitled.articleviewcount.domain

import com.devfloor.untitled.article.domain.Article
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
class ArticleViewCount(
    article: Article,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_view_count_id")
    val id: Long = 0

    @OneToOne
    @JoinColumn(name = "article_id")
    val article: Article = article

    @Column(name = "viewCount")
    var viewCount: Long = 1
        protected set

    fun increase() = this.viewCount++
}
