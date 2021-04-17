package com.devfloor.untitled.articleOption.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleOptionRepository : JpaRepository<ArticleOption, Long> {
    fun findAllByArticle(article: Article): List<ArticleOption>?
}
