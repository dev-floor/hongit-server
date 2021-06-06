package com.devfloor.untitled.comment.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.article.domain.ArticleRepository
import com.devfloor.untitled.comment.application.request.CommentCreateRequest
import com.devfloor.untitled.comment.application.request.CommentModifyRequest
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
            ?: EntityNotFoundException.notExistsId(Article::class, articleId)

        return comments.map {
            CommentResponse(
                comment = it,
                favoriteCount = commentFavoriteRepository.countAllByComment(it),
            )
        }
    }

    @Transactional
    fun create(author: User, request: CommentCreateRequest): CommentResponse {
        val article = articleRepository.findByIdOrNull(request.articleId)
            ?: EntityNotFoundException.notExistsId(Article::class, request.articleId)

        return commentRepository.save(request.toComment(article, author))
            .let(::CommentResponse)
    }

    @Transactional
    fun modifyByCommentId(commentId: Long, request: CommentModifyRequest): CommentResponse =
        commentRepository.findByIdOrNull(commentId)
            ?.let { comment ->
                comment.modifyContent(request.content)
                CommentResponse(comment)
            }
            ?: EntityNotFoundException.notExistsId(Comment::class, commentId)

    @Transactional
    fun destroyByCommentId(commentId: Long) {
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: EntityNotFoundException.notExistsId(Comment::class, commentId)

        commentFavoriteRepository.deleteAllByComment(comment)
        commentRepository.delete(comment)
    }
}
