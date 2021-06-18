package com.devfloor.untitled.article.application

import com.devfloor.untitled.article.application.request.ArticleCreateRequest
import com.devfloor.untitled.article.application.request.ArticleModifyRequest
import com.devfloor.untitled.article.application.response.ArticleFeedResponse
import com.devfloor.untitled.article.application.response.ArticleResponse
import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.article.domain.ArticleRepository
import com.devfloor.untitled.articlefavorite.domain.ArticleFavoriteRepository
import com.devfloor.untitled.articlehashtag.application.ArticleHashtagService
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtagRepository
import com.devfloor.untitled.articlehitscount.application.ArticleHitsCountService
import com.devfloor.untitled.articlehitscount.domain.ArticleHitsCountRepository
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import com.devfloor.untitled.articleoption.domain.ArticleOption
import com.devfloor.untitled.articleoption.domain.ArticleOptionRepository
import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.board.domain.BoardRepository
import com.devfloor.untitled.common.exception.EntityNotFoundException
import com.devfloor.untitled.hashtag.application.HashtagService
import com.devfloor.untitled.option.domain.OptionRepository
import com.devfloor.untitled.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val articleFavoriteRepository: ArticleFavoriteRepository,
    private val articleHashtagRepository: ArticleHashtagRepository,
    private val articleOptionRepository: ArticleOptionRepository,
    private val boardRepository: BoardRepository,
    private val optionRepository: OptionRepository,
    private val articleHitsCountRepository: ArticleHitsCountRepository,

    private val articleHashtagService: ArticleHashtagService,
    private val articleOptionService: ArticleOptionService,
    private val hashtagService: HashtagService,
    private val articleHitsCountService: ArticleHitsCountService,
) {
    @Transactional(readOnly = true)
    fun showByArticleId(articleId: Long): ArticleResponse {
        val article = articleRepository.findByIdOrNull(articleId)
            ?: EntityNotFoundException.notExistsId(Article::class, articleId)

        val articleOptions = articleOptionRepository.findAllByArticle(article)
        val articleHashtags = articleHashtagRepository.findAllByArticle(article)
        val articleFavorites = articleFavoriteRepository.findAllByArticle(article)

        articleHitsCountService.increase(article)

        return ArticleResponse(
            article = article,
            articleOptions = articleOptions,
            articleHashtags = articleHashtags,
            articleFavorites = articleFavorites,
        )
    }

    @Transactional(readOnly = true)
    fun showAllByBoardId(boardId: Long): List<ArticleFeedResponse> {
        val board = boardRepository.findByIdOrNull(boardId)
            ?: EntityNotFoundException.notExistsId(Board::class, boardId)

        return articleRepository.findAllByBoard(board)
            .map { article ->
                val articleOptions = articleOptionRepository.findAllByArticle(article)
                val articleFavorites = articleFavoriteRepository.findAllByArticle(article)

                ArticleFeedResponse(
                    article = article,
                    articleOptions = articleOptions,
                    articleFavorites = articleFavorites,
                )
            }
    }

    @Transactional
    fun create(request: ArticleCreateRequest, author: User): Long {
        val article = boardRepository.findByIdOrNull(request.boardId)
            ?.let { articleRepository.save(request.toArticle(author, it)) }
            ?: EntityNotFoundException.notExistsId(Board::class, request.boardId)

        optionRepository.findAllById(request.optionIds)
            .map { ArticleOption(article, it) }
            .let { articleOptionRepository.saveAll(it) }

        hashtagService.createAllByNames(request.hashtagNames)
            .map { ArticleHashtag(article, it) }
            .let { articleHashtagRepository.saveAll(it) }

        return article.id
    }

    @Transactional
    fun modifyByArticleId(articleId: Long, request: ArticleModifyRequest) {
        val article = articleRepository.findByIdOrNull(articleId)
            ?.apply { modify(request.title, request.content) }
            ?: EntityNotFoundException.notExistsId(Article::class, articleId)

        hashtagService.createAllByNames(request.hashtagNames)
            .let { articleHashtagService.modifyByArticle(article, it) }

        optionRepository.findAllById(request.optionIds)
            .let { articleOptionService.modifyByArticle(article, it) }
    }

    @Transactional
    fun destroyByArticleId(articleId: Long) = articleRepository.findByIdOrNull(articleId)
        ?.let {
            articleOptionRepository.deleteAllByArticle(it)
            articleHashtagRepository.deleteAllByArticle(it)
            articleFavoriteRepository.deleteAllByArticle(it)
            articleRepository.delete(it)
            articleHitsCountRepository.deleteByArticle(it)
        }
        ?: EntityNotFoundException.notExistsId(Article::class, articleId)
}
