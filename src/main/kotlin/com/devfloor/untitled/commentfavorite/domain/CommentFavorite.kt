package com.devfloor.untitled.commentfavorite.domain

import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.common.domain.BaseEntity
import com.devfloor.untitled.user.domain.User
import javax.persistence.*

/**
 * 댓글에 누른 좋아요 연관관계 entity
 *
 * @property id 아이디
 * @property comment 좋아요를 누른 댓글
 * @property user 좋아요를 누른 사람
 */
@Entity
@Table(name = "comment_articles")
class CommentFavorite(
    comment: Comment,
    user: User,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_article_id")
    var id: Long? = null
        protected set

    @ManyToOne
    @JoinColumn(name = "comment_id")
    var comment: Comment = comment
        protected set

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User = user
        protected set
}
