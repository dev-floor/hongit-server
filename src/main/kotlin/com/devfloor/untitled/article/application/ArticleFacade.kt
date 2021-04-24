package com.devfloor.untitled.article.application

import com.devfloor.untitled.articlefavorite.application.ArticleFavoriteService
import com.devfloor.untitled.articlehashtag.application.ArticleHashtagService
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import com.devfloor.untitled.user.application.ProfileResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleFacade(
    private val articleService: ArticleService,
    private val articleHashtagService: ArticleHashtagService,
    private val articleFavoriteService: ArticleFavoriteService,
    private val articleOptionService: ArticleOptionService
) {
    @Transactional
    fun create(articleRequest: ArticleRequest) {
        //
    }

    @Transactional(readOnly = true)
    fun showByArticleId(articleId: Long): ArticleResponse {
        val article = articleService.showById(articleId)
        val hashtags = articleHashtagService.showAllByArticle(article)
            .map { it.hashtag.name }
        val favorites = articleFavoriteService.showAllByArticle(article)
        val options = articleOptionService.showAllByArticle(article)
            .map { it.option.type.name }

        return ArticleResponse(
            options = options,
            title = article.title,
            anonymous = article.anonymous,
            content = article.content,
            author = ProfileResponse(article.author),
            createdDate = article.createdDate,
            modifiedDate = article.modifiedDate,
            hashtags = hashtags,
            favorites = favorites.count { it.type.isFavorite() }.toLong(),
            wonders = favorites.count { it.type.isWonder() }.toLong(),
            clips = favorites.count { it.type.isClip() }.toLong(),
        )
    }
}
