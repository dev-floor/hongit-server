package com.devfloor.untitled.article.application

import com.devfloor.untitled.articlehashtag.application.ArticleHashtagService
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import com.devfloor.untitled.favorite.application.FavoriteService
import com.devfloor.untitled.favorite.domain.FavoriteType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ArticleFacade(
    private val articleService: ArticleService,
    private val articleHashtagService: ArticleHashtagService,
    private val favoriteService: FavoriteService,
    private val articleOptionService: ArticleOptionService,
) {
    @Transactional(readOnly = true)
    fun getArticle(articleId: Long): ArticleResponse {
        val article = articleService.findById(articleId)
        val hashtags = articleHashtagService.findAllByArticle(article)
            .map { it.hashtag.name }
        val favorites = favoriteService.findAllByArticle(article)
        val options = articleOptionService.findAllByArticle(article)
            .map { it.option.type.toString() }

        return ArticleResponse(
            options = options,
            title = article.title,
            createdDate = article.createdDate.toString(),
            content = article.content,
            hashtags = hashtags,
            favorites = favorites.count { it.type == FavoriteType.FAVORITE }.toLong(),
            wonders = favorites.count { it.type == FavoriteType.WONDER }.toLong(),
            clips = favorites.count { it.type == FavoriteType.CLIP }.toLong(),
            author = article.author
        )
    }
}
