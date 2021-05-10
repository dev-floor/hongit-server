package com.devfloor.untitled.articlehashtag.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface ArticleHashtagRepository : JpaRepository<ArticleHashtag, Long> {
    @Transactional(readOnly = true)
    fun findAllByArticle(article: Article): List<ArticleHashtag>
}
