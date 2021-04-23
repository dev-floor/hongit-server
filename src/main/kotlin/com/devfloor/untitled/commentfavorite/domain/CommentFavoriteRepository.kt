package com.devfloor.untitled.commentfavorite.domain

import com.devfloor.untitled.comment.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentFavoriteRepository : JpaRepository<CommentFavorite, Long> {
    fun countAllByComment(comment: Comment): Long
}
