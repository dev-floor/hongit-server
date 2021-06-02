package com.devfloor.untitled.commentfavorite.domain

import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.common.domain.BaseEntity
import com.devfloor.untitled.user.domain.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 댓글에 누른 좋아요를 관리하는 entity
 *
 * @property id 아이디
 * @property comment 좋아요를 누른 댓글
 * @property user 좋아요를 누른 사람
 */
@Entity
@Table(name = "comment_favorites")
class CommentFavorite(
    comment: Comment,
    user: User,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_favorite_id")
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "comment_id")
    val comment: Comment = comment

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User = user
}
