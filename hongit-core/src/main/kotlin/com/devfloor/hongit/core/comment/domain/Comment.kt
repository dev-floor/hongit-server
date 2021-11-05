package com.devfloor.hongit.core.comment.domain

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.common.domain.BaseEntity
import com.devfloor.hongit.core.user.domain.User
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
    id: Long = 0,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    val id: Long = id

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

    fun withId(id: Long): Comment = Comment(article, author, anonymous, content, id)

    fun modifyContent(content: String) {
        this.content = content
    }
}
