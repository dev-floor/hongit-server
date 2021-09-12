package com.devfloor.hongit.api.comment.application

import com.devfloor.hongit.api.comment.application.request.CommentCreateRequest
import com.devfloor.hongit.api.comment.application.request.CommentModifyRequest
import com.devfloor.hongit.api.comment.application.response.CommentInProfileResponse
import com.devfloor.hongit.api.comment.application.response.CommentResponse
import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.article.domain.ArticleRepository
import com.devfloor.hongit.core.comment.domain.Comment
import com.devfloor.hongit.core.comment.domain.CommentRepository
import com.devfloor.hongit.core.commentfavorite.domain.CommentFavoriteRepository
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserRepository
import com.devfloor.hongit.core.user.domain.findByNicknameOrNull
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val articleRepository: ArticleRepository,
    private val commentRepository: CommentRepository,
    private val commentFavoriteRepository: CommentFavoriteRepository,
    private val userRepository: UserRepository,
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

    @Transactional(readOnly = true)
    fun showAllByNickname(nickname: String): List<CommentInProfileResponse> {
        val comments = userRepository.findByNicknameOrNull(nickname)
            ?.let(commentRepository::findAllByAuthor)
            ?: EntityNotFoundException.notExistsNickname(User::class, nickname)

        return comments
            .filter { !it.anonymous }
            .map {
                CommentInProfileResponse(
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
        // TODO: 2021/09/11 validate 로직 필요
        commentRepository.findByIdOrNull(commentId)
            ?.let { comment ->
                comment.modifyContent(request.content)
                CommentResponse(comment)
            }
            ?: EntityNotFoundException.notExistsId(Comment::class, commentId)

    @Transactional
    fun destroyByCommentId(commentId: Long) { // TODO: 2021/09/11 validate 로직 필요
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: EntityNotFoundException.notExistsId(Comment::class, commentId)

        commentFavoriteRepository.deleteAllByComment(comment)
        commentRepository.delete(comment)
    }
}
