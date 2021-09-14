package com.devfloor.hongit.api.article.application.response

import com.devfloor.hongit.api.common.utils.SEOUL_TIME_ZONE
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavorite
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.hongit.core.common.utils.LOCAL_DATE_TIME_FORMAT
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ArticleHomeResponse(
    val articleId: Long,

    val title: String? = null,

    val favoriteCount: Long,

    val wonderCount: Long,

    val clipCount: Long,

    @JsonFormat(
        pattern = LOCAL_DATE_TIME_FORMAT,
        shape = JsonFormat.Shape.STRING,
        locale = SEOUL_TIME_ZONE
    )
    val createdAt: LocalDateTime,

    @JsonFormat(
        pattern = LOCAL_DATE_TIME_FORMAT,
        shape = JsonFormat.Shape.STRING,
        locale = SEOUL_TIME_ZONE
    )
    val modifiedAt: LocalDateTime,
) {
    constructor(
        article: Article,
        articleFavorites: List<ArticleFavorite>,
    ) : this(
        articleId = article.id,
        title = article.title,
        favoriteCount = articleFavorites.count { it.matchesType(ArticleFavoriteType.FAVORITE) }.toLong(),
        wonderCount = articleFavorites.count { it.matchesType(ArticleFavoriteType.WONDER) }.toLong(),
        clipCount = articleFavorites.count { it.matchesType(ArticleFavoriteType.CLIP) }.toLong(),
        createdAt = article.createdAt,
        modifiedAt = article.modifiedAt,
    )
}
