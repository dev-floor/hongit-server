package com.devfloor.hongit.core.commentfavorite.domain

import com.devfloor.hongit.core.comment.domain.Comment
import com.devfloor.hongit.core.user.domain.User
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
    fun deleteAllByComment(comment: Comment)

    @Transactional
    fun deleteByCommentAndUser(comment: Comment, user: User)
}
