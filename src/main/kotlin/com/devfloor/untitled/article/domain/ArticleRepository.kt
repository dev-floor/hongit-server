package com.devfloor.untitled.article.domain

import com.devfloor.untitled.articlefavorite.domain.QArticleFavorite
import com.devfloor.untitled.articleviewcount.domain.QArticleViewCount
import com.devfloor.untitled.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByBoard(board: Board): List<Article>
    fun findTop5ByBoardOrderByCreatedAtDesc(board: Board): List<Article>
}

interface ArticleRepositoryCustom {

    fun findByFavoriteTop5(): List<Article>
    fun findByViewCountTop5(): List<Article>
}

@Repository
class ArticleRepositoryImpl :
    QuerydslRepositorySupport(Article::class.java), ArticleRepositoryCustom {
    override fun findByFavoriteTop5(): List<Article> {
        TODO("Not yet implemented")
        val articleTable = QArticle.article
        val articleFavoriteTable = QArticleFavorite.articleFavorite
        return from(articleTable)
            .leftJoin(articleFavoriteTable).on(articleTable.eq(articleFavoriteTable.article))
            .fetchJoin()
            .groupBy(articleTable)
            .orderBy(articleFavoriteTable.count().desc())
            .limit(5)
            .fetch()
    }

    override fun findByViewCountTop5(): List<Article> {
        TODO("Not yet implemented")
        val articleTable = QArticle.article
        val articleViewCountTable = QArticleViewCount.articleViewCount
        return from(articleTable)
            .innerJoin(articleViewCountTable).on(articleTable.eq(articleViewCountTable.article))
            .fetchJoin()
            .orderBy(articleViewCountTable.count.desc())
            .limit(5)
            .fetch()
    }
}
