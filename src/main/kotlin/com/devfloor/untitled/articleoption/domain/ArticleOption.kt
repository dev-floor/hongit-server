package com.devfloor.untitled.articleoption.domain

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.option.domain.Option
import javax.persistence.*

@Entity
@Table(name = "article_option")
class ArticleOption(
    article: Article,
    option: Option,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_option_id")
    var id: Long? = null
        protected set

    @ManyToOne
    @JoinColumn(name = "article_id")
    val article: Article = article

    @ManyToOne
    @JoinColumn(name = "option_id")
    val option: Option = option
}
