package com.devfloor.untitled.comment.application

import com.devfloor.untitled.article.application.ArticleService
import com.devfloor.untitled.comment.application.request.CommentRequest
import com.devfloor.untitled.comment.application.response.CommentResponse
import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.comment.domain.CommentRepository
import com.devfloor.untitled.commentfavorite.application.CommentFavoriteService
import com.devfloor.untitled.common.exception.EntityNotFoundException
import com.devfloor.untitled.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val articleService: ArticleService,
    private val commentFavoriteService: CommentFavoriteService,
) {
    @Transactional(readOnly = true)
    fun showAllByArticleId(articleId: Long): List<CommentResponse> {
        val comments = articleService.showByArticleIdOrNull(articleId)
            .let(commentRepository::findAllByArticle)

        return comments.map {
            CommentResponse(
                comment = it,
                favorites = commentFavoriteService.countAllByComment(it),
            )
        }
    }

    @Transactional
    fun create(
        articleId: Long,
        author: User,
        request: CommentRequest,
    ): CommentResponse {
        val article = articleService.showByArticleIdOrNull(articleId)

        return Comment(article, author, request.anonymous, request.content)
            .let(commentRepository::save)
            .let(::CommentResponse)
    }

    private fun showByCommentId(commentId: Long): Comment =
        commentRepository.findByIdOrNull(commentId)
            ?: throw EntityNotFoundException("존재하지 않는 댓글입니다.")

    @Transactional
    fun modifyByCommentId(commentId: Long, request: CommentModifyRequest) {
        showByCommentId(commentId).modifyContent(request.content)
    }
}
