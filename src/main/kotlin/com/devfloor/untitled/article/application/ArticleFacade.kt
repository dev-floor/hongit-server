package com.devfloor.untitled.article.application

import com.devfloor.untitled.articlehashtag.application.ArticleHashtagService
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import com.devfloor.untitled.favorite.application.ArticleFavoriteService
import com.devfloor.untitled.favorite.domain.ArticleFavoriteType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleFacade(
    private val articleService: ArticleService,
    private val articleHashtagService: ArticleHashtagService,
    private val favoriteService: ArticleFavoriteService,
    private val articleOptionService: ArticleOptionService
) {
    @Transactional(readOnly = true)
    fun show(articleId: Long): ArticleResponse {
        val article = articleService.showById(articleId)
        val hashtags = articleHashtagService.showAllByArticle(article)
            .map { it.hashtag.name }
        val favorites = favoriteService.showAllByArticle(article)
        val options = articleOptionService.showAllByArticle(article)
            .map { it.option.type.name }

        return ArticleResponse(
            options = options,
            title = article.title,
            anonymous = article.anonymous,
            content = article.content,
            author = article.author,
            createdDate = article.createdDate.toString(),
            modifiedDate = article.modifiedDate.toString(),
            hashtags = hashtags,
            favorites = favorites.count { it.type == ArticleFavoriteType.FAVORITE }.toLong(),
            wonders = favorites.count { it.type == ArticleFavoriteType.WONDER }.toLong(),
            clips = favorites.count { it.type == ArticleFavoriteType.CLIP }.toLong(),
        )
    }
}
