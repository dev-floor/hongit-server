package com.devfloor.untitled.favorite.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.favorite.domain.Favorite
import com.devfloor.untitled.favorite.domain.FavoriteRepository

class FavoriteService(
        private val repository: FavoriteRepository
) {

    fun findByArticle(article: Article): List<Favorite>? = repository.findByArticle(article)
}
