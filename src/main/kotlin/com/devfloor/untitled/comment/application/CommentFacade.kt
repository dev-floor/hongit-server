package com.devfloor.untitled.comment.application

import com.devfloor.untitled.article.application.ArticleService
import com.devfloor.untitled.commentfavorite.domain.CommentFavoriteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentFacade(
    private val commentService: CommentService,
    private val articleService: ArticleService,
    private val commentFavoriteRepository: CommentFavoriteRepository,
) {
    @Transactional(readOnly = true)
    fun showAllByArticleId(articleId: Long) = articleService.showById(articleId)
        .let { commentService.showAllByArticle(it) }
        .map {
            CommentResponse(
                user = it.author,
                comment = it,
                favorites = commentFavoriteRepository.countAllByComment(it)
            )
        }
}
