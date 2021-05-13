package com.devfloor.untitled.article.application

import com.devfloor.untitled.articlefavorite.application.ArticleFavoriteService
import com.devfloor.untitled.articlehashtag.application.ArticleHashtagService
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleFacade(
    private val articleService: ArticleService,
    private val articleHashtagService: ArticleHashtagService,
    private val articleFavoriteService: ArticleFavoriteService,
    private val articleOptionService: ArticleOptionService
) {
    @Transactional(readOnly = true)
    fun showByArticleId(articleId: Long): ArticleResponse {
        val article = articleService.showById(articleId)
        val options = articleOptionService.showAllByArticle(article)
        val hashtags = articleHashtagService.showAllByArticle(article)
        val favorites = articleFavoriteService.showAllByArticle(article)

        return ArticleResponse(
            options = options,
            article = article,
            hashtags = hashtags,
            favorites = favorites,
        )
    }
}
