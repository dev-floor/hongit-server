package com.devfloor.untitled.articleclickcount.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * 게시글 좋아요를 관리하는 repository
 */
@Transactional(readOnly = true)
interface ArticleClickCountRepository : JpaRepository<ArticleClickCount, Long> {
    fun findByArticle(article: Article): Optional<ArticleClickCount>

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM ArticleClickCount WHERE article = :article")
    fun deleteByArticle(article: Article)
}

fun ArticleClickCountRepository.findByArticleOrNull(article: Article): ArticleClickCount? =
    findByArticle(article).orElse(null)
