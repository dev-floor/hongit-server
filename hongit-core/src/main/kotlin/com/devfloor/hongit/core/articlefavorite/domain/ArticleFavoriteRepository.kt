package com.devfloor.hongit.core.articlefavorite.domain

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface ArticleFavoriteRepository : JpaRepository<ArticleFavorite, Long> {
    fun findAllByArticle(article: Article): List<ArticleFavorite>

    fun findAllByUser(favoritedUser: User): List<ArticleFavorite>

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM ArticleFavorite WHERE article = :article")
    fun deleteAllByArticle(article: Article)

    @Transactional
    fun deleteByArticleAndUser(article: Article, user: User)
}
