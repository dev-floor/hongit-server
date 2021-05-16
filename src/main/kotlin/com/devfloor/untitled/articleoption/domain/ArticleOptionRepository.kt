package com.devfloor.untitled.articleoption.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

@Transactional(readOnly = true)
interface ArticleOptionRepository : JpaRepository<ArticleOption, Long> {
    fun findAllByArticle(article: Article): List<ArticleOption>

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM ArticleOption WHERE article = :article") // TODO : queryDSL 로?
    fun deleteAllByArticle(article: Article)
}
