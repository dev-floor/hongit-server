package com.devfloor.untitled.article.application

import com.devfloor.untitled.article.application.request.ArticleModifyRequest
import com.devfloor.untitled.article.application.request.ArticleRequest
import com.devfloor.untitled.article.application.response.ArticleResponse
import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.article.domain.ArticleRepository
import com.devfloor.untitled.articlefavorite.application.ArticleFavoriteService
import com.devfloor.untitled.articlehashtag.application.ArticleHashtagService
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import com.devfloor.untitled.common.exception.EntityNotFoundException
import com.devfloor.untitled.hashtag.application.HashtagService
import com.devfloor.untitled.option.application.OptionService
import com.devfloor.untitled.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(
    private val repository: ArticleRepository,
    private val articleHashtagService: ArticleHashtagService,
    private val articleFavoriteService: ArticleFavoriteService,
    private val articleOptionService: ArticleOptionService,
    private val hashtagService: HashtagService,
    private val optionService: OptionService,
) {
    fun showByArticleIdOrNull(articleId: Long) = repository.findByIdOrNull(articleId)
        ?: throw EntityNotFoundException("사용자가 요청한 리소스가 없습니다")

    @Transactional(readOnly = true)
    fun showByArticleId(articleId: Long): ArticleResponse =
        showByArticleIdOrNull(articleId)
            .let {
                val articleOptions = articleOptionService.showAllByArticle(it)
                val articleHashtags = articleHashtagService.showAllByArticle(it)
                val articleFavorites = articleFavoriteService.showAllByArticle(it)

                ArticleResponse(
                    articleOptions = articleOptions,
                    article = it,
                    articleHashtags = articleHashtags,
                    articleFavorites = articleFavorites,
                )
            }

    fun showAll(): List<Article> = repository.findAll()

    @Transactional
    fun create(request: ArticleRequest, user: User): Long {
        val article = repository.save(request.toArticle(user))

        if (request.hasOptions) {
            optionService.showAllByOptionType(request.options)
                .let { articleOptionService.createAll(article, it) }
        }

        request.hashtags
            .map { hashtagService.createByName(it) }
            .map { articleHashtagService.create(ArticleHashtag(article, it)) }

        return article.id
    }

    @Transactional
    fun modifyByArticleId(
        articleId: Long,
        request: ArticleModifyRequest,
    ) {
        val article = showByArticleIdOrNull(articleId)
            .apply { modify(request.title, request.content) }

        request.hashtags
            .map { hashtagService.createByName(it) }
            .let { articleHashtagService.modifyByArticle(article, it) }

        optionService.showAllByOptionType(request.options)
            .let { articleOptionService.modifyByArticle(article, it) }
    }

    fun destroyByArticleId(articleId: Long) = repository.deleteById(articleId)
}
