package com.devfloor.untitled.favorite.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository

interface FavoriteRepository : JpaRepository<Favorite, Long> {
    fun findAllByArticle(article: Article): List<Favorite>?
}
