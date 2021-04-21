package com.devfloor.untitled.articlehashtag.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleHashtagRepository : JpaRepository<ArticleHashtag, Long> {
    fun findAllByArticle(article: Article): List<ArticleHashtag>
}
