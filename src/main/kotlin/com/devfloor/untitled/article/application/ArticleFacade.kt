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
    fun getArticle(articleId: Long): GetArticleResponse =
        articleService.findById(articleId).run {
            val hashtags = articleHashtagService.findByArticle(this)
                .map { it.hashtag.toString() }

            val favorites = favoriteService.findByArticle(this)

            GetArticleResponse(
                option = option.name,
                title = title,
                createdDate = createdDate.toString(),
                content = content,
                hashtags = hashtags,
                favorites = favorites.count { it.type == FavoriteType.FAVORITE }.toLong(),
                wonders = favorites.count { it.type == FavoriteType.WONDER }.toLong(),
                clips = favorites.count { it.type == FavoriteType.CLIP }.toLong(),
                author = author // TODO()
            )
        }
}
