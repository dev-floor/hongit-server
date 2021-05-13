package com.devfloor.untitled.articleoption.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface ArticleOptionRepository : JpaRepository<ArticleOption, Long> {

    fun findAllByArticle(article: Article): List<ArticleOption>
}
