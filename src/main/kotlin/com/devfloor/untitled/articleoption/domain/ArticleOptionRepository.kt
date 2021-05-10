package com.devfloor.untitled.articleoption.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface ArticleOptionRepository : JpaRepository<ArticleOption, Long> {
    @Transactional(readOnly = true)
    fun findAllByArticle(article: Article): List<ArticleOption>
}
