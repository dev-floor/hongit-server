package com.devfloor.hongit.api.commentfavorite.application

import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.core.comment.domain.Comment
import com.devfloor.hongit.core.comment.domain.CommentRepository
import com.devfloor.hongit.core.commentfavorite.domain.CommentFavorite
import com.devfloor.hongit.core.commentfavorite.domain.CommentFavoriteRepository
import com.devfloor.hongit.core.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentFavoriteService(
    private val commentRepository: CommentRepository,
    private val commentFavoriteRepository: CommentFavoriteRepository,
) {
    fun create(commentId: Long, user: User): Long {
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: EntityNotFoundException.notExistsId(Comment::class, commentId)

        if (commentFavoriteRepository.existsByCommentAndUser(comment, user)) {
            throw IllegalArgumentException("해당 comment에 대한 좋아요가 이미 존재합니다.")
        }

        return commentFavoriteRepository.save(CommentFavorite(comment, user))
            .id
    }

    fun destroy(commentId: Long, user: User) = commentRepository.findByIdOrNull(commentId)
        ?.let { commentFavoriteRepository.deleteByCommentAndUser(it, user) }
        ?: EntityNotFoundException.notExistsId(Comment::class, commentId)
}
