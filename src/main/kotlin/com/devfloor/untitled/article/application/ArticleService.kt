package com.devfloor.untitled.article.application

import com.devfloor.untitled.article.application.request.ArticleModifyRequest
import com.devfloor.untitled.article.application.request.ArticleRequest
import com.devfloor.untitled.article.application.response.ArticleResponse
import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.article.domain.ArticleRepository
import com.devfloor.untitled.articlefavorite.domain.ArticleFavoriteRepository
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
    private val articleRepository: ArticleRepository,
    private val articleFavoriteRepository: ArticleFavoriteRepository,
    private val articleHashtagService: ArticleHashtagService,
    private val articleOptionService: ArticleOptionService,
    private val hashtagService: HashtagService,
    private val optionService: OptionService,
) {
    @Transactional(readOnly = true)
    fun showByArticleId(articleId: Long): ArticleResponse {
        return articleRepository.findByIdOrNull(articleId)
            ?.let {
                val articleOptions = articleOptionService.showAllByArticle(it)
                val articleHashtags = articleHashtagService.showAllByArticle(it)
                val articleFavorites = articleFavoriteRepository.findAllByArticle(it)

                ArticleResponse(
                    articleOptions = articleOptions,
                    article = it,
                    articleHashtags = articleHashtags,
                    articleFavorites = articleFavorites,
                )
            }
        ?: throw EntityNotFoundException("존재하지 않는 게시글 입니다.")
    }

    fun showAll(): List<Article> = articleRepository.findAll()

    @Transactional
    fun create(request: ArticleRequest, user: User): Long {
        val article = articleRepository.save(request.toArticle(user))

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
        val article = articleRepository.findByIdOrNull(articleId)
            ?.apply { modify(request.title, request.content) }
            ?: throw EntityNotFoundException("존재하지 않는 게시글 입니다.")

        request.hashtags
            .map { hashtagService.createByName(it) }
            .let { articleHashtagService.modifyByArticle(article, it) }

        optionService.showAllByOptionType(request.options)
            .let { articleOptionService.modifyByArticle(article, it) }
    }

    fun destroyByArticleId(articleId: Long) = articleRepository.deleteById(articleId)
}
