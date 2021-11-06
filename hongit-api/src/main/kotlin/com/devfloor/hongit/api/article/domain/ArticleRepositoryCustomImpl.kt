package com.devfloor.hongit.api.article.domain

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.article.domain.QArticle.article
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.hongit.core.articlefavorite.domain.QArticleFavorite.articleFavorite
import com.devfloor.hongit.core.articleviewcount.domain.QArticleViewCount.articleViewCount
import com.devfloor.hongit.core.board.domain.Board
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : ArticleRepositoryCustom {
    override fun findByFavoriteTopFive(): List<Article> {
        return jpaQueryFactory
            .selectFrom(article)
            .join(articleFavorite).on(article.eq(articleFavorite.article))
            .where(articleFavorite.type.eq(ArticleFavoriteType.FAVORITE))
            .groupBy(article)
            .orderBy(articleFavorite.type.count().desc())
            .limit(5)
            .fetch()
    }

    override fun findByViewCountTopFive(): List<Article> {
        return jpaQueryFactory
            .selectFrom(article)
            .join(articleViewCount).on(article.eq(articleViewCount.article))
            .orderBy(articleViewCount.count.desc())
            .limit(5)
            .fetch()
    }

    override fun findAllByBoardOrderByViewCount(board: Board, pageRequest: Pageable): List<Article> {
        return jpaQueryFactory
            .selectFrom(article)
            .join(articleViewCount).on(article.eq(articleViewCount.article))
            .where(article.board.eq(board))
            .orderBy(articleViewCount.count.desc())
            .offset(pageRequest.offset)
            .limit(pageRequest.pageSize.toLong())
            .fetch()
    }

    override fun findAllByBoardOrderByFavorite(board: Board, pageable: Pageable): Page<Article> {
        val result = jpaQueryFactory
            .selectFrom(article)
            .join(articleFavorite).on(article.id.eq(articleFavorite.article.id))
            .where(
                articleFavorite.type.eq(ArticleFavoriteType.FAVORITE),
                article.board.id.eq(board.id)
            )
            .groupBy(article.id)
            .orderBy(articleFavorite.type.count().desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()

        return PageImpl(result.results, pageable, result.total)
    }
}
