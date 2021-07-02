package com.devfloor.untitled.commentfavorite.application

import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.comment.domain.CommentRepository
import com.devfloor.untitled.commentfavorite.domain.CommentFavorite
import com.devfloor.untitled.commentfavorite.domain.CommentFavoriteRepository
import com.devfloor.untitled.common.exception.EntityNotFoundException
import com.devfloor.untitled.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentFavoriteService(
    private val commentRepository: CommentRepository,
    private val commentFavoriteRepository: CommentFavoriteRepository,
) {
    fun create(commentId: Long, user: User) = commentRepository.findByIdOrNull(commentId)
        ?.let { commentFavoriteRepository.save(CommentFavorite(it, user)) }
        ?: EntityNotFoundException.notExistsId(Comment::class, commentId)

    fun destroy(commentId: Long, user: User) = commentRepository.findByIdOrNull(commentId)
        ?.let { commentFavoriteRepository.deleteByCommentAndUser(it, user) }
        ?: EntityNotFoundException.notExistsId(Comment::class, commentId)
}
