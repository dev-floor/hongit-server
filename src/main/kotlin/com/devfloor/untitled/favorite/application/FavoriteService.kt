package com.devfloor.untitled.favorite.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.favorite.domain.Favorite
import com.devfloor.untitled.favorite.domain.FavoriteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavoriteService(
    private val repository: FavoriteRepository
) {
    @Transactional(readOnly = true)
    fun findByArticle(article: Article): List<Favorite> =
        repository.findByArticle(article) ?: emptyList()
}
