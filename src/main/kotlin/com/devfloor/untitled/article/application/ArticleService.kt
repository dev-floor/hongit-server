package com.devfloor.untitled.article.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.article.domain.ArticleRepository
import com.devfloor.untitled.articlefavorite.domain.ArticleFavoriteRepository
import com.devfloor.untitled.articleoption.domain.ArticleOptionRepository
import com.devfloor.untitled.board.domain.BoardRepository
import com.devfloor.untitled.common.exception.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val boardRepository: BoardRepository,
    private val articleFavoriteRepository: ArticleFavoriteRepository,
    private val articleOptionRepository: ArticleOptionRepository,
) {
    fun showById(id: Long): Article = articleRepository.findByIdOrNull(id)
        ?: throw EntityNotFoundException("사용자가 요청한 리소스가 없습니다")

    fun showAll(): List<Article> = articleRepository.findAll()

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

    fun create(article: Article): Article = articleRepository.save(article)

    fun modify(id: Long, title: String?, content: String): Article =
        showById(id).apply { modify(title, content) }
}
