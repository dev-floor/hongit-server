package com.devfloor.untitled.comment.application

import com.devfloor.untitled.article.application.ArticleService
import com.devfloor.untitled.commentfavorite.application.CommentFavoriteService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentFacade(
    private val commentService: CommentService,
    private val articleService: ArticleService,
    private val commentFavoriteService: CommentFavoriteService,
) {
    @Transactional(readOnly = true)
    fun showAllByArticleId(articleId: Long): List<CommentResponse> {
        return articleService.showById(articleId)
            .let { commentService.showAllByArticle(it) }
            .map {
                CommentResponse(
                    comment = it,
                    favorites = commentFavoriteService.countAllByComment(it),
                )
            }
    }
}
