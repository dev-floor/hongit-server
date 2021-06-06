package com.devfloor.untitled.articleoption.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

/**
 * 게시글과 옵션의 연관관계를 관리하는 repository
 */
@Transactional(readOnly = true)
interface ArticleOptionRepository : JpaRepository<ArticleOption, Long> {
    fun findAllByArticle(article: Article): List<ArticleOption>

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM ArticleOption WHERE article = :article")
    fun deleteAllByArticle(article: Article)
}
