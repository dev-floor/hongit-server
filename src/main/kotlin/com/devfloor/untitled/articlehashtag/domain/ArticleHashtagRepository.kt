package com.devfloor.untitled.articlehashtag.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

@Transactional(readOnly = true)
interface ArticleHashtagRepository : JpaRepository<ArticleHashtag, Long> {
    fun findAllByArticle(article: Article): List<ArticleHashtag>

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM ArticleHashtag WHERE article = :article") // TODO : queryDSL 로?
    fun deleteAllByArticle(article: Article)
}
