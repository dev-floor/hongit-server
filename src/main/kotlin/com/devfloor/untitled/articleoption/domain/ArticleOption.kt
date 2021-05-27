package com.devfloor.untitled.articleoption.domain

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.common.domain.BaseEntity
import com.devfloor.untitled.option.domain.Option
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 게시물과 옵션 정보를 관리하는 연관관계 매핑 entity
 *
 * @property id 아이디
 * @property article 게시글
 * @property option 옵션
 */
@Entity
@Table(name = "article_options")
class ArticleOption(
    article: Article,
    option: Option,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_option_id")
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "article_id")
    val article: Article = article

    @ManyToOne
    @JoinColumn(name = "option_id")
    val option: Option = option
}
