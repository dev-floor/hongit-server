package com.devfloor.hongit.core.articleviewcount.domain

import com.devfloor.hongit.core.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Transactional(readOnly = true)
interface ArticleViewCountRepository : JpaRepository<ArticleViewCount, Long> {
    fun findByArticle(article: Article): Optional<ArticleViewCount>

    @Transactional
    fun deleteByArticle(article: Article)
}

fun ArticleViewCountRepository.findByArticleOrNull(article: Article): ArticleViewCount? =
    findByArticle(article).orElse(null)
