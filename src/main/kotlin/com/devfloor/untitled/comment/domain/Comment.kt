package com.devfloor.untitled.comment.domain

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.common.domain.BaseEntity
import com.devfloor.untitled.user.domain.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.Lob
import javax.persistence.ManyToOne
import javax.persistence.Table

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
@Table(
    name = "comments",
    indexes = [
        Index(name = "idx_article_id", columnList = "article_id")
    ]
)
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

    @Column(name = "anonymous")
    var anonymous: Boolean = anonymous
        protected set

    @Lob
    @Column(name = "content")
    var content: String = content
        protected set

    fun modifyContent(content: String) {
        this.content = content
    }
}
