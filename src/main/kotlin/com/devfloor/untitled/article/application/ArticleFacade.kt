package com.devfloor.untitled.article.application

import com.devfloor.untitled.articleHashtag.application.ArticleHashtagService
import com.devfloor.untitled.favorite.application.FavoriteService
import com.devfloor.untitled.favorite.domain.FavoriteType
import com.devfloor.untitled.option.application.OptionService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ArticleFacade(
    private val articleService: ArticleService,
    private val articleHashtagService: ArticleHashtagService,
    private val favoriteService: FavoriteService,
    private val optionService: OptionService
) {
    @Transactional(readOnly = true)
    fun getArticle(articleId: Long): ArticleResponse {
        val article = articleService.findById(articleId)
        val hashtags = articleHashtagService.findAllByArticle(article)
            .map { it.hashtag.toString() }
        val favorites = favoriteService.findAllByArticle(article)
        val options = optionService.findAllByArticle(article)
            .map {it.type.toString() }

        return ArticleResponse(
            options = options,
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
