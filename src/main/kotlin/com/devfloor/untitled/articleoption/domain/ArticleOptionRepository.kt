package com.devfloor.untitled.articleoption.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface ArticleOptionRepository : JpaRepository<ArticleOption, Long> {
    fun findAllByArticle(article: Article): List<ArticleOption>

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM ArticleOption WHERE article = :article") // TODO: queryDSL 도입시 변경
    fun deleteAllByArticle(article: Article)
}
