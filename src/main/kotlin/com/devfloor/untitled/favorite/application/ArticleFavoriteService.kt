package com.devfloor.untitled.favorite.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.favorite.domain.ArticleFavorite
import com.devfloor.untitled.favorite.domain.ArticleFavoriteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleFavoriteService(
    private val repository: ArticleFavoriteRepository
) {
    @Transactional(readOnly = true)
    fun findAllByArticle(article: Article): List<ArticleFavorite> =
        repository.findAllByArticle(article)
}
