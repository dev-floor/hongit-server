package com.devfloor.untitled.board.application

import com.devfloor.untitled.article.application.ArticleService
import com.devfloor.untitled.article.application.ArticlesResponse
import com.devfloor.untitled.articlefavorite.application.ArticleFavoriteService
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardFacade(
    private val articleService: ArticleService,
    private val articleFavoriteService: ArticleFavoriteService,
    private val articleOptionService: ArticleOptionService
) {
    @Transactional(readOnly = true)
    fun showById(): BoardResponse {
        val articles = articleService.showAll().map { article ->
            val favorites = articleFavoriteService.showAllByArticle(article)
            val options = articleOptionService.showAllByArticle(article)
                .map { it.option.type.name }

            val content = if (article.content.length > CONTENT_UPPER_BOUND)
                article.content.substring(0, CONTENT_UPPER_BOUND + 1)
            else
                article.content

            ArticlesResponse(
                id = article.id,
                options = options,
                title = article.title,
                anonymous = article.anonymous,
                authorName = article.author.nickname,
                createdDate = article.createdDate,
                modifiedDate = article.modifiedDate,
                content = content,
                favorites = favorites.count { it.type.isFavorite() }.toLong(),
                wonders = favorites.count { it.type.isWonder() }.toLong(),
                clips = favorites.count { it.type.isClip() }.toLong(),
            )
        }

        return BoardResponse(
            articles = articles
        )
    }

    companion object {
        private const val CONTENT_UPPER_BOUND = 300
    }
}
