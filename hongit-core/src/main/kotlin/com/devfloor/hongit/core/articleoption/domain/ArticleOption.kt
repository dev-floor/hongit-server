package com.devfloor.hongit.core.articleoption.domain

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.common.domain.BaseEntity
import com.devfloor.hongit.core.option.domain.Option
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 게시물과 옵션의 연관관계 매핑 entity
 *
 * @property id 아이디
 * @property article 게시글
 * @property option 옵션
 */
@Entity
@Table(
    name = "article_options",
    indexes = [
        Index(name = "idx_article_id", columnList = "article_id")
    ]
)
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
