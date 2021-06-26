package com.devfloor.untitled.articleviewcount.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * 게시글 좋아요를 관리하는 repository
 */
@Transactional(readOnly = true)
interface ArticleViewCountRepository : JpaRepository<ArticleViewCount, Long> {
    fun findByArticle(article: Article): Optional<ArticleViewCount>

    @Transactional
    fun deleteByArticle(article: Article)
}

fun ArticleViewCountRepository.findByArticleOrNull(article: Article): ArticleViewCount? =
    findByArticle(article).orElse(null)
