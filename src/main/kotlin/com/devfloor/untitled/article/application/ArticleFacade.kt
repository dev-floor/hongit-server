package com.devfloor.untitled.article.application

import com.devfloor.untitled.articlefavorite.application.ArticleFavoriteService
import com.devfloor.untitled.articlehashtag.application.ArticleHashtagService
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import com.devfloor.untitled.articleoption.domain.ArticleOption
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
    private val optionService: OptionService,
    private val hashtagService: HashtagService,
) {
    @Transactional
    fun create(request: ArticleRequest, user: User): Long {
        val article = articleService.create(request.toArticle(request, user))
        if (request.isOptionsNotEmpty()) {
            optionService.showAllByOptionType(request.options)
                .map { ArticleOption(article, it) }
                .let { articleOptionService.createAll(it) }
        }
        request.hashtags.map { hashtagService.createByName(it) }
            .map { articleHashtagService.create(ArticleHashtag(article, it)) }

        return article.id
    }

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
