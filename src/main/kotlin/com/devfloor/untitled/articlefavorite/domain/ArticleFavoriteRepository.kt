package com.devfloor.untitled.articlefavorite.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface ArticleFavoriteRepository : JpaRepository<ArticleFavorite, Long> {

    fun findAllByArticle(article: Article): List<ArticleFavorite>
}
