package com.devfloor.untitled.articlefavorite.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlefavorite.domain.ArticleFavorite
import com.devfloor.untitled.articlefavorite.domain.ArticleFavoriteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleFavoriteService(
    private val repository: ArticleFavoriteRepository
) {
    @Transactional(readOnly = true)
    fun showAllByArticle(article: Article): List<ArticleFavorite> {
        return repository.findAllByArticle(article)
    }
}
