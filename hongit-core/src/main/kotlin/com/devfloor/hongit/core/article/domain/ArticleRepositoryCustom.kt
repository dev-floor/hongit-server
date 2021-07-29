package com.devfloor.hongit.core.article.domain

import com.devfloor.hongit.core.article.domain.QArticle.article
import com.devfloor.hongit.core.articlefavorite.domain.QArticleFavorite.articleFavorite
import com.devfloor.hongit.core.articleviewcount.domain.QArticleViewCount.articleViewCount
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class ArticleRepositoryCustom(
    val entityManager: EntityManager,
    val jpaQueryFactory: JPAQueryFactory,
) {
    fun findByFavoriteTopFive(): List<Article> {
        return jpaQueryFactory.selectFrom(article)
            .leftJoin(articleFavorite).on(article.eq(articleFavorite.article))
            .fetchJoin()
            .groupBy(article)
            .orderBy(articleFavorite.count().desc())
            .limit(5)
            .fetch()
    }

    fun findByViewCountTopFive(): List<Article> {
        return jpaQueryFactory.selectFrom(article)
            .innerJoin(articleViewCount).on(article.eq(articleViewCount.article))
            .fetchJoin()
            .orderBy(articleViewCount.count.desc())
            .limit(5)
            .fetch()
    }
}
