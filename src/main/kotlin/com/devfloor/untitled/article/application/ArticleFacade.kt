package com.devfloor.untitled.article.application

import com.devfloor.untitled.articlefavorite.application.ArticleFavoriteService
import com.devfloor.untitled.articlehashtag.application.ArticleHashtagService
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import com.devfloor.untitled.hashtag.application.HashtagService
import com.devfloor.untitled.option.application.OptionService
import com.devfloor.untitled.user.application.ProfileResponse
import com.devfloor.untitled.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleFacade(
    private val articleService: ArticleService,
    private val articleHashtagService: ArticleHashtagService,
    private val articleFavoriteService: ArticleFavoriteService,
    private val articleOptionService: ArticleOptionService,
    private val optionService: OptionService,
    private val hashtagService: HashtagService
) {
    @Transactional
    fun create(articleRequest: ArticleRequest, user: User): Long {
        val article = articleService.create(articleRequest.toArticleWithUser(articleRequest, user))
        if (!articleRequest.options.isNullOrEmpty()) {
            optionService.showAllByOptionType(articleRequest.options)
                .let { articleOptionService.createAllByOptions(article, it) }
        }
        if (!articleRequest.hashtags.isNotEmpty()) {
            val hashtags = hashtagService.showAllByNames(articleRequest.hashtags)
            if (hashtags.isNullOrEmpty()) {
                hashtagService.createHashtags(articleRequest.hashtags).let {
                    articleHashtagService.createAllByHashtags(article, it)
                }
            } else {
                articleHashtagService.createAllByHashtags(article, hashtags)
            }
        }
        return article.id
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
