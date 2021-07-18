package com.devfloor.hongit.api.article.application.response

import com.devfloor.hongit.api.common.utils.LOCAL_DATE_TIME_FORMAT
import com.devfloor.hongit.api.common.utils.SEOUL_TIME_ZONE
import com.devfloor.hongit.api.option.application.response.OptionResponse
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavorite
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.hongit.core.articlehashtag.domain.ArticleHashtag
import com.devfloor.hongit.core.articleoption.domain.ArticleOption
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
        favoriteCount = articleFavorites.count { it.matchesType(ArticleFavoriteType.FAVORITE) }.toLong(),
        wonderCount = articleFavorites.count { it.matchesType(ArticleFavoriteType.WONDER) }.toLong(),
        clipCount = articleFavorites.count { it.matchesType(ArticleFavoriteType.CLIP) }.toLong(),
        createdAt = article.createdAt,
        modifiedAt = article.modifiedAt,
    )
}
