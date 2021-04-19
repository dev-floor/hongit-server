package com.devfloor.untitled.article.application

import com.devfloor.untitled.articlehashtag.application.ArticleHashtagService
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import com.devfloor.untitled.favorite.application.FavoriteService
import com.devfloor.untitled.favorite.domain.FavoriteType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleFacade(
    private val articleService: ArticleService,
    private val articleHashtagService: ArticleHashtagService,
    private val favoriteService: FavoriteService,
    private val articleOptionService: ArticleOptionService
) {
    @Transactional(readOnly = true)
    fun getArticle(articleId: Long): ArticleResponse {
        val article = articleService.findById(articleId)
        val hashtags = articleHashtagService.findAllByArticle(article)
            .map { it.hashtag.name }
        val favorites = favoriteService.findAllByArticle(article)
        val options = articleOptionService.findAllByArticle(article)
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
            favorites = favorites.count { it.type == FavoriteType.FAVORITE }.toLong(),
            wonders = favorites.count { it.type == FavoriteType.WONDER }.toLong(),
            clips = favorites.count { it.type == FavoriteType.CLIP }.toLong(),
        )
    }
}
