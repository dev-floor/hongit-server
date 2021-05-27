package com.devfloor.untitled.board.application

import com.devfloor.untitled.article.application.response.ArticleFeedResponse
import com.devfloor.untitled.article.application.ArticleService
import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlefavorite.application.ArticleFavoriteService
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import com.devfloor.untitled.board.application.response.BoardResponse
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
        val articleFavorites = articleFavoriteService.showAllByArticle(article)
        val articleOptions = articleOptionService.showAllByArticle(article)

        return ArticleFeedResponse(
            articleOptions = articleOptions,
            article = article,
            articleFavorites = articleFavorites,
        )
    }
}
