package com.devfloor.untitled.article.application

import com.devfloor.untitled.articleHashtag.application.ArticleHashtagService
import com.devfloor.untitled.favorite.application.FavoriteService
import com.devfloor.untitled.favorite.domain.FavoriteType
import org.springframework.stereotype.Component

@Component
class ArticleFacade(
    private val articleService: ArticleService,
    private val articleHashtagService: ArticleHashtagService,
    private val favoriteService: FavoriteService
) {
    fun getArticle(articleId: Long): GetArticleResponse {
        val article = articleService.findById(articleId)
        val hashtags = articleHashtagService.findByArticle(article)
            .map { it.hashtag.toString() }
        val favorites = favoriteService.findByArticle(article)

        return GetArticleResponse(
            option = article.option.name,
            title = article.title,
            createdDate = article.createdDate.toString(),
            content = article.content,
            hashtags = hashtags,
            favorites = favorites.count { it.type == FavoriteType.FAVORITE }.toLong(),
            wonders = favorites.count { it.type == FavoriteType.WONDER }.toLong(),
            clips = favorites.count { it.type == FavoriteType.CLIP }.toLong(),
            author = article.author // TODO()
        )
    }
}
