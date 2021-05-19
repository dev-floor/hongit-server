package com.devfloor.untitled.comment.application

import com.devfloor.untitled.article.application.ArticleService
import com.devfloor.untitled.comment.application.request.CommentRequest
import com.devfloor.untitled.comment.application.response.CommentResponse
import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.comment.domain.CommentRepository
import com.devfloor.untitled.commentfavorite.application.CommentFavoriteService
import com.devfloor.untitled.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val repository: CommentRepository,
    private val articleService: ArticleService,
    private val commentFavoriteService: CommentFavoriteService,
) {
    @Transactional(readOnly = true)
    fun showAllByArticleId(articleId: Long): List<CommentResponse> {
        val comments = articleService.showById(articleId)
            .let { repository.findAllByArticle(it) }

        return comments.map {
            CommentResponse(
                comment = it,
                favorites = commentFavoriteService.countAllByComment(it)
            )
        }
    }

    @Transactional
    fun create(
        articleId: Long,
        author: User,
        request: CommentRequest,
    ): Comment {
        val article = articleService.showById(articleId)

        return Comment(article, author, request.anonymous, request.content)
            .let { repository.save(it) }
            .also { CommentResponse(it) }
    }
}
