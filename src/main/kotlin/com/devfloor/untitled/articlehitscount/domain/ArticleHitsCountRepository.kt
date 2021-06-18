package com.devfloor.untitled.articlehitscount.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface ArticleHitsCountRepository : JpaRepository<ArticleHitsCount, Long> {
    fun findByArticleOrNull(article: Article): ArticleHitsCount?

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM ArticleHitsCount WHERE article = :article")
    fun deleteByArticle(article: Article)
}
