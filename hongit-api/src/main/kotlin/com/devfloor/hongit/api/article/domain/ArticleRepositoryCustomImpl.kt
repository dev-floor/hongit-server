package com.devfloor.hongit.api.article.domain

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.article.domain.QArticle.article
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.hongit.core.articlefavorite.domain.QArticleFavorite.articleFavorite
import com.devfloor.hongit.core.articleviewcount.domain.QArticleViewCount.articleViewCount
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : ArticleRepositoryCustom {
    override fun findByFavoriteTopFive(): List<Article> {
        return jpaQueryFactory.selectFrom(article)
            .join(articleFavorite).on(article.eq(articleFavorite.article))
            .where(articleFavorite.type.eq(ArticleFavoriteType.FAVORITE))
            .groupBy(article)
            .orderBy(articleFavorite.type.count().desc())
            .limit(5)
            .fetch()
    }

    override fun findByViewCountTopFive(): List<Article> {
        return jpaQueryFactory.selectFrom(article)
            .join(articleViewCount).on(article.eq(articleViewCount.article))
            .orderBy(articleViewCount.count.desc())
            .limit(5)
            .fetch()
    }
}
