package com.devfloor.untitled.commentfavorite.domain

import com.devfloor.untitled.comment.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface CommentFavoriteRepository : JpaRepository<CommentFavorite, Long> {
    @Transactional(readOnly = true)
    fun countAllByComment(comment: Comment): Long
}
