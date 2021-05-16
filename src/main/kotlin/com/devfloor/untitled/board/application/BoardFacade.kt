package com.devfloor.untitled.board.application

import com.devfloor.untitled.article.application.ArticleFeedResponse
import com.devfloor.untitled.article.application.ArticleService
import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlefavorite.application.ArticleFavoriteService
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardFacade(
    private val articleService: ArticleService,
    private val articleFavoriteService: ArticleFavoriteService,
    private val articleOptionService: ArticleOptionService,
) {
    @Transactional(readOnly = true)
    fun showByBoardId(): BoardResponse {
        val articles = articleService.showAll()
            .map { createArticleFeedResponse(it) }

        return BoardResponse(
            articles = articles
        )
    }

    private fun createArticleFeedResponse(article: Article): ArticleFeedResponse {
        val favorites = articleFavoriteService.showAllByArticle(article)
        val options = articleOptionService.showAllByArticle(article)

        return ArticleFeedResponse(
            options = options,
            article = article,
            articleFavorites = favorites
        )
    }
}
