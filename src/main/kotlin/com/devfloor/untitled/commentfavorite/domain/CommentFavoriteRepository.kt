package com.devfloor.untitled.commentfavorite.domain

import com.devfloor.untitled.comment.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface CommentFavoriteRepository : JpaRepository<CommentFavorite, Long> {
    fun countAllByComment(comment: Comment): Long

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM CommentFavorite WHERE comment = :comment")
    fun deleteByComment(comment: Comment)
}
