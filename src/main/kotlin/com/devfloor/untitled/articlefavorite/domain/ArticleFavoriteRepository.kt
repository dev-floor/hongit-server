package com.devfloor.untitled.articlefavorite.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleFavoriteRepository : JpaRepository<ArticleFavorite, Long> {
    fun findAllByArticle(article: Article): List<ArticleFavorite>

}
