package com.devfloor.untitled.comment.domain

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.common.domain.BaseEntity
import com.devfloor.untitled.user.domain.User
import javax.persistence.*

/**
 * 댓글을 관리하는 entity
 *
 * @property id 아이디
 * @property article 댓글을 작성한 게시글
 * @property author 댓글을 작성한 사람
 * @property anonymous 댓글의 익명 여부
 * @property content 댓글 내용
 */
@Entity
@Table(name = "comments")
class Comment(
    article: Article,
    author: User,
    anonymous: Boolean,
    content: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "article_id")
    val article: Article = article

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: User = author

    @Column
    var anonymous: Boolean = anonymous
        protected set

    @Lob
    @Column(name = "content")
    var content: String = content
        protected set

    fun modify(comment: Comment) {
        this.content = comment.content
    }
}
