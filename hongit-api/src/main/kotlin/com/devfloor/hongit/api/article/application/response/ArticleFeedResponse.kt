package com.devfloor.hongit.api.article.application.response

import com.devfloor.hongit.api.common.utils.SEOUL_TIME_ZONE
import com.devfloor.hongit.api.option.application.response.OptionResponse
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavorite
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.hongit.core.articleoption.domain.ArticleOption
import com.devfloor.hongit.core.common.utils.LOCAL_DATE_TIME_FORMAT
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ArticleFeedResponse(
    val id: Long,

    val options: List<OptionResponse>,

    val title: String? = null,

    val anonymous: Boolean,

    val authorName: String,

    val content: String,

    val favoriteCount: Long,

    val wonderCount: Long,

    val clipCount: Long,

    val page: Int,

    val totalArticleCount: Int,

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
        articleOptions: List<ArticleOption>,
        articleFavorites: List<ArticleFavorite>,
        page: Int,
        totalArticleCount: Int,
    ) : this(
        id = article.id,
        options = articleOptions.map { OptionResponse(it.option) },
        title = article.title,
        anonymous = article.anonymous,
        authorName = article.author.nickname,
        content = article.sliceContentByLength(CONTENT_MAX_LENGTH),
        favoriteCount = articleFavorites.count { it.matchesType(ArticleFavoriteType.FAVORITE) }.toLong(),
        wonderCount = articleFavorites.count { it.matchesType(ArticleFavoriteType.WONDER) }.toLong(),
        clipCount = articleFavorites.count { it.matchesType(ArticleFavoriteType.CLIP) }.toLong(),
        page = page,
        totalArticleCount = totalArticleCount,
        createdAt = article.createdAt,
        modifiedAt = article.modifiedAt,
    )

    companion object {
        private const val CONTENT_MAX_LENGTH: Long = 300
    }
}
