package com.devfloor.untitled.article.domain

import com.devfloor.untitled.common.domain.BaseEntity
import com.devfloor.untitled.user.domain.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 게시물 정보를 관리하는 entity
 *
 * @property id 아이디
 * @property title 게시글 제목
 * @property anonymous 익명
 * @property content 게시글 내용
 * @property author 작성자
 */
@Entity
@Table(name = "article")
class Article(
    title: String?,
    anonymous: Boolean,
    content: String,
    author: User,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    var id: Long? = null
        protected set

    @Column(name = "title")
    var title: String? = title
        protected set

    @Column(name = "anonymous")
    var anonymous: Boolean = anonymous
        protected set

    @Column(name = "content")
    var content: String = content
        protected set

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: User = author
}
