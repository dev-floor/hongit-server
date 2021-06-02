package com.devfloor.untitled.comment.application

import com.devfloor.untitled.article.domain.ArticleRepository
import com.devfloor.untitled.comment.application.request.CommentModifyRequest
import com.devfloor.untitled.comment.application.request.CommentRequest
import com.devfloor.untitled.comment.application.response.CommentResponse
import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.comment.domain.CommentRepository
import com.devfloor.untitled.commentfavorite.domain.CommentFavoriteRepository
import com.devfloor.untitled.common.exception.EntityNotFoundException
import com.devfloor.untitled.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val articleRepository: ArticleRepository,
    private val commentRepository: CommentRepository,
    private val commentFavoriteRepository: CommentFavoriteRepository,
) {
    @Transactional(readOnly = true)
    fun showAllByArticleId(articleId: Long): List<CommentResponse> {
        val comments = articleRepository.findByIdOrNull(articleId)
            ?.let(commentRepository::findAllByArticle)
            ?: throw EntityNotFoundException("존재하지 않는 게시글 입니다.")

        return comments.map {
            CommentResponse(
                comment = it,
                favorites = commentFavoriteRepository.countAllByComment(it),
            )
        }
    }

    @Transactional
    fun create(author: User, request: CommentRequest): CommentResponse {
        val article = articleRepository.findByIdOrNull(request.articleId)
            ?: throw EntityNotFoundException("존재하지 않는 게시글 입니다.")

        return Comment(article, author, request.anonymous, request.content)
            .let(commentRepository::save)
            .let(::CommentResponse)
    }

    @Transactional
    fun modifyByCommentId(commentId: Long, request: CommentModifyRequest) {
        commentRepository.findByIdOrNull(commentId)
            ?.modifyContent(request.content)
            ?: throw EntityNotFoundException("존재하지 않는 댓글입니다.")
    }
}
