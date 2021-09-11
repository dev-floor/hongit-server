package com.devfloor.hongit.api.article.application

import com.devfloor.hongit.api.article.application.request.ArticleCreateRequest
import com.devfloor.hongit.api.article.application.request.ArticleModifyRequest
import com.devfloor.hongit.api.article.application.response.ArticleFeedResponse
import com.devfloor.hongit.api.article.application.response.ArticleHomeResponse
import com.devfloor.hongit.api.article.application.response.ArticleResponse
import com.devfloor.hongit.api.article.domain.ArticleRepositoryCustom
import com.devfloor.hongit.api.article.domain.ArticleSortType
import com.devfloor.hongit.api.articlehashtag.application.ArticleHashtagService
import com.devfloor.hongit.api.articleoption.application.ArticleOptionService
import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.hashtag.application.HashtagService
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.article.domain.ArticleRepository
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavorite
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteRepository
import com.devfloor.hongit.core.articlehashtag.domain.ArticleHashtag
import com.devfloor.hongit.core.articlehashtag.domain.ArticleHashtagRepository
import com.devfloor.hongit.core.articleoption.domain.ArticleOption
import com.devfloor.hongit.core.articleoption.domain.ArticleOptionRepository
import com.devfloor.hongit.core.articleviewcount.domain.ArticleViewCount
import com.devfloor.hongit.core.articleviewcount.domain.ArticleViewCountRepository
import com.devfloor.hongit.core.articleviewcount.domain.findByArticleOrNull
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.board.domain.BoardRepository
import com.devfloor.hongit.core.option.domain.OptionRepository
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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
    private val articleViewCountRepository: ArticleViewCountRepository,
    private val userRepository: UserRepository,
    private val articleRepositoryCustom: ArticleRepositoryCustom,

    private val articleHashtagService: ArticleHashtagService,
    private val articleOptionService: ArticleOptionService,
    private val hashtagService: HashtagService,

    ) {
    @Transactional(readOnly = true)
    fun showByArticleId(articleId: Long): ArticleResponse {
        val article = articleRepository.findByIdOrNull(articleId)
            ?: EntityNotFoundException.notExistsId(Article::class, articleId)

        val articleOptions = articleOptionRepository.findAllByArticle(article)
        val articleHashtags = articleHashtagRepository.findAllByArticle(article)
        val articleFavorites = articleFavoriteRepository.findAllByArticle(article)

        increaseViewCount(article)

        return ArticleResponse(
            article = article,
            articleOptions = articleOptions,
            articleHashtags = articleHashtags,
            articleFavorites = articleFavorites,
        )
    }

    @Transactional(readOnly = true)
    fun showAllByBoardId(
        boardId: Long,
        page: Int,
        pageSize: Int,
        sort: ArticleSortType?,
        options: List<Long>?
    ): List<ArticleFeedResponse> {
        val board = boardRepository.findByIdOrNull(boardId)
            ?: EntityNotFoundException.notExistsId(Board::class, boardId)

        return when (sort) {
            ArticleSortType.CREATED -> {
                val pageRequest: PageRequest = PageRequest.of(page, pageSize, Sort.by("createdAt"))
                articleRepository.findAllByBoard(board, pageRequest).content
                    .map {
                        ArticleFeedResponse(
                            article = it,
                            articleOptions = articleOptionRepository.findAllByArticle(it),
                            articleFavorites = articleFavoriteRepository.findAllByArticle(it),
                            page = page,
                            totalArticleCount = articleRepository.countAllByBoard(board)
                        )
                    }
            }
            ArticleSortType.VIEW_COUNT -> {
                val pageRequest: PageRequest = PageRequest.of(page, pageSize)
                articleRepositoryCustom.findAllByBoardSortByViewCount(board, pageRequest).map {
                    ArticleFeedResponse(
                        article = it,
                        articleOptions = articleOptionRepository.findAllByArticle(it),
                        articleFavorites = articleFavoriteRepository.findAllByArticle(it),
                        page = page,
                        totalArticleCount = articleRepository.countAllByBoard(board)
                    )
                }
            }
            ArticleSortType.FAVORITE -> {
                val pageRequest: PageRequest = PageRequest.of(page, pageSize, Sort.by("createdAt"))
                articleRepositoryCustom.findAllByBoardSortByFavorite(board, pageRequest).map {
                    ArticleFeedResponse(
                        article = it,
                        articleOptions = articleOptionRepository.findAllByArticle(it),
                        articleFavorites = articleFavoriteRepository.findAllByArticle(it),
                        page = page,
                        totalArticleCount = articleRepository.countAllByBoard(board)
                    )
                }
            }
            else -> throw IllegalArgumentException()
        }
    }

    fun showTopFiveByFavorite(): List<ArticleHomeResponse> {
        return articleRepositoryCustom.findByFavoriteTopFive()
            .map {
                ArticleHomeResponse(
                    article = it,
                    articleFavorites = articleFavoriteRepository.findAllByArticle(it),
                )
            }
    }

    fun showTopFiveByViewCount(): List<ArticleHomeResponse> {
        return articleRepositoryCustom.findByViewCountTopFive()
            .map {
                ArticleHomeResponse(
                    article = it,
                    articleFavorites = articleFavoriteRepository.findAllByArticle(it),
                )
            }
    }

    fun showTopFiveByBoard(board: Board): List<ArticleHomeResponse> {
        return articleRepository.findTop5ByBoardOrderByCreatedAtDesc(board)
            .map {
                ArticleHomeResponse(
                    article = it,
                    articleFavorites = articleFavoriteRepository.findAllByArticle(it),
                )
            }
    }

    @Transactional(readOnly = true)
    fun showAllByUserId(userId: Long, page: Int, pageSize: Int): List<ArticleFeedResponse> {
        val user: User = userRepository.findByIdOrNull(userId)
            ?: EntityNotFoundException.notExistsId(User::class, userId)

        val pageRequest: PageRequest = PageRequest.of(page, pageSize)
        val articles = user
            .let { articleRepository.findAllByAuthor(it, pageRequest) }

        return articles.content
            .map {
                ArticleFeedResponse(
                    article = it,
                    articleOptions = articleOptionRepository.findAllByArticle(it),
                    articleFavorites = articleFavoriteRepository.findAllByArticle(it),
                    page = page,
                    totalArticleCount = articleRepository.countAllByAuthor(user)
                )
            }
    }

    @Transactional(readOnly = true)
    fun showAllByUserIdNotAnonymous(userId: Long, page: Int, pageSize: Int): List<ArticleFeedResponse> {
        val user: User = userRepository.findByIdOrNull(userId)
            ?: EntityNotFoundException.notExistsId(User::class, userId)

        val pageRequest: PageRequest = PageRequest.of(page, pageSize)
        val articles = user
            .let { articleRepository.findAllByAuthorAndAnonymousFalse(it, pageRequest) }

        return articles.content
            .map {
                ArticleFeedResponse(
                    article = it,
                    articleOptions = articleOptionRepository.findAllByArticle(it),
                    articleFavorites = articleFavoriteRepository.findAllByArticle(it),
                    page = page,
                    totalArticleCount = articleRepository.countAllByAuhorAndAndAnonymousFalse(
                        user
                    )
                )
            }
    }

    @Transactional(readOnly = true)
    fun showAllByFavoritedUserId(userId: Long, page: Int, pageSize: Int): List<ArticleFeedResponse> {
        val user: User = userRepository.findByIdOrNull(userId)
            ?: EntityNotFoundException.notExistsId(User::class, userId)
        val articleFavorites: List<ArticleFavorite> = articleFavoriteRepository.findAllByUser(user)
        val pageRequest: PageRequest = PageRequest.of(page, pageSize)
        val articles = articleFavorites
            .let {
                articleRepository.findAllByIdIn(it.map { it.id }, pageRequest)
            }

        return articles.content
            .map {
                ArticleFeedResponse(
                    article = it,
                    articleOptions = articleOptionRepository.findAllByArticle(it),
                    articleFavorites = articleFavoriteRepository.findAllByArticle(it),
                    page = page,
                    totalArticleCount = articleRepository.countAllByIdIn(articleFavorites.map { it.id }
                    )
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
            articleViewCountRepository.deleteByArticle(it)
        }
        ?: EntityNotFoundException.notExistsId(Article::class, articleId)

    private fun increaseViewCount(article: Article) {
        articleViewCountRepository.findByArticleOrNull(article)
            ?.run { ::increase }
            ?: articleViewCountRepository.save(ArticleViewCount(article))
    }
}
