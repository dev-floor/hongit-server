package com.devfloor.untitled.articlefavorite.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlefavorite.domain.ArticleFavorite
import com.devfloor.untitled.articlefavorite.domain.ArticleFavoriteRepository
import org.springframework.stereotype.Service

@Service
class ArticleFavoriteService(
    private val repository: ArticleFavoriteRepository
) {
    fun showAllByArticle(article: Article): List<ArticleFavorite> =
        repository.findAllByArticle(article)
}
