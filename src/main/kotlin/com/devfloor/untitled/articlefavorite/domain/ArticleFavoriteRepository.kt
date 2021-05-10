package com.devfloor.untitled.articlefavorite.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface ArticleFavoriteRepository : JpaRepository<ArticleFavorite, Long> {
    @Transactional(readOnly = true)
    fun findAllByArticle(article: Article): List<ArticleFavorite>
}
