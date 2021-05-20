package com.devfloor.untitled.article.application

import com.devfloor.untitled.articlefavorite.application.ArticleFavoriteService
import com.devfloor.untitled.articlehashtag.application.ArticleHashtagService
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import com.devfloor.untitled.hashtag.application.HashtagService
import com.devfloor.untitled.option.application.OptionService
import com.devfloor.untitled.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleFacade(
    private val articleService: ArticleService,
    private val articleHashtagService: ArticleHashtagService,
    private val articleFavoriteService: ArticleFavoriteService,
    private val articleOptionService: ArticleOptionService,
    private val hashtagService: HashtagService,
    private val optionService: OptionService,
) {
    @Transactional
    fun create(request: ArticleRequest, user: User): Long {
        val article = articleService.create(request.toArticle(request, user))
        if (request.isOptionsNotEmpty) {
            optionService.showAllByOptionType(request.options)
                .let { articleOptionService.createAll(article, it) }
        }
        request.hashtags
            .map { hashtagService.createByName(it) }
            .map { articleHashtagService.create(ArticleHashtag(article, it)) }

        return article.id
    }

    @Transactional(readOnly = true)
    fun showByArticleId(articleId: Long): ArticleResponse =
        articleService.showById(articleId)
            .let {
                val articleOptions = articleOptionService.showAllByArticle(it)
                val articleHashtags = articleHashtagService.showAllByArticle(it)
                val articleFavorites = articleFavoriteService.showAllByArticle(it)

                ArticleResponse(
                    options = articleOptions,
                    article = it,
                    hashtags = articleHashtags,
                    favorites = articleFavorites,
                )
            }

    @Transactional
    fun modifyByArticleId(
        articleId: Long,
        request: ArticleModifyRequest
    ) {
        val article = articleService.modify(articleId, request.title, request.content)

        request.hashtags
            .map { hashtagService.createByName(it) }
            .let { articleHashtagService.modifyByArticle(article, it) }

        optionService.showAllByOptionType(request.options)
            .let { articleOptionService.modifyByArticle(article, it) }
    }
}
