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
    private val articleRepository: ArticleRepository,
    private val boardRepository: BoardRepository,
    private val articleFavoriteRepository: ArticleFavoriteRepository,
    private val articleOptionRepository: ArticleOptionRepository,
) {
    @Transactional(readOnly = true)
    fun showByArticleId(articleId: Long): ArticleResponse {
        val article = articleRepository.findByIdOrNull(articleId)
            ?: throw EntityNotFoundException("존재하지 않는 게시글 입니다.")

        val articleOptions = articleOptionService.showAllByArticle(article)
        val articleHashtags = articleHashtagService.showAllByArticle(article)
        val articleFavorites = articleFavoriteRepository.findAllByArticle(article)

        return ArticleResponse(
            articleOptions = articleOptions,
            article = article,
            articleHashtags = articleHashtags,
            articleFavorites = articleFavorites,
        )
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

    fun showAllByBoardId(boardId: Long): List<ArticleFeedResponse> {
        val board = boardRepository.findByIdOrNull(boardId)
            ?: throw EntityNotFoundException("id에 해당하는 board가 존재하지 않습니다 - boardId: $boardId")

        return articleRepository.findAllByBoard(board)
            .map {
                val articleOptions = articleOptionRepository.findAllByArticle(it)
                val articleFavorites = articleFavoriteRepository.findAllByArticle(it)

                ArticleFeedResponse(
                    articleOptions = articleOptions,
                    article = it,
                    articleFavorites = articleFavorites,
                )
            }
    }

    @Transactional
    fun destroyByArticleId(articleId: Long) {
        val article = articleRepository.findByIdOrNull(articleId)
            ?: throw EntityNotFoundException("존재하지 않는 게시글 입니다.")

        articleHashtagService.destroyAllByArticle(article)
        articleOptionService.destroyAllByArticle(article)
        articleFavoriteRepository.deleteAllByArticle(article)
        articleRepository.delete(article)
    }
}
