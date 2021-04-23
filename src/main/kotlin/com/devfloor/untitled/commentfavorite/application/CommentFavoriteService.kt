package com.devfloor.untitled.commentfavorite.application

import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.commentfavorite.domain.CommentFavoriteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentFavoriteService(
    private val repository: CommentFavoriteRepository,
) {
    @Transactional(readOnly = true)
    fun countAllByComment(comment: Comment): Long = repository.countAllByComment(comment)
}
