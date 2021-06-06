package com.devfloor.untitled.article.application.response

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlefavorite.domain.ArticleFavorite
import com.devfloor.untitled.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articleoption.domain.ArticleOption
import com.devfloor.untitled.common.utils.LOCAL_DATE_TIME_FORMAT
import com.devfloor.untitled.common.utils.SEOUL_TIME_ZONE
import com.devfloor.untitled.option.application.response.OptionResponse
import com.devfloor.untitled.user.application.ProfileResponse
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ArticleResponse(
    val id: Long,

    val options: List<OptionResponse>,

    val title: String? = null,

    val anonymous: Boolean,

    val author: ProfileResponse,

    val content: String,

    val hashtags: List<String>,

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
        articleOptions: List<ArticleOption>,
        articleHashtags: List<ArticleHashtag>,
        articleFavorites: List<ArticleFavorite>,
    ) : this(
        id = article.id,
        options = articleOptions.map { OptionResponse(it.option) },
        title = article.title,
        anonymous = article.anonymous,
        content = article.content,
        author = ProfileResponse(article.author),
        hashtags = articleHashtags.map { it.hashtag.name },
        favoriteCount = articleFavorites.count { it.matchType(ArticleFavoriteType.FAVORITE) }.toLong(),
        wonderCount = articleFavorites.count { it.matchType(ArticleFavoriteType.WONDER) }.toLong(),
        clipCount = articleFavorites.count { it.matchType(ArticleFavoriteType.CLIP) }.toLong(),
        createdAt = article.createdAt,
        modifiedAt = article.modifiedAt,
    )
}
