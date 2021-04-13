package com.devfloor.untitled.articleHashtag.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleHashtagRepository : JpaRepository<ArticleHashtag, Long> {

    fun findAllByArticle(article: Article): List<Article>?
}