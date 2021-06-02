package com.devfloor.untitled.commentfavorite.domain

import com.devfloor.untitled.comment.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

/**
 * 댓글의 좋아요를 관리하는 repository
 */
@Transactional(readOnly = true)
interface CommentFavoriteRepository : JpaRepository<CommentFavorite, Long> {
    fun countAllByComment(comment: Comment): Long
}
