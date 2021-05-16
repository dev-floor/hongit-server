package com.devfloor.untitled.article.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlefavorite.domain.ArticleFavorite
import com.devfloor.untitled.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articleoption.domain.ArticleOption
import com.devfloor.untitled.user.application.ProfileResponse
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ArticleResponse(
    val options: List<String>,

    val title: String? = null,

    val anonymous: Boolean,

    val content: String,

    val author: ProfileResponse,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val createdDate: LocalDateTime,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val modifiedDate: LocalDateTime,

    val hashtags: List<String>,

    val favorites: Long,

    val wonders: Long,

    val clips: Long,
) {
    constructor(
        options: List<ArticleOption>,
        article: Article,
        hashtags: List<ArticleHashtag>,
        favorites: List<ArticleFavorite>,
    ) : this(
        options = options.map { it.option.type.name },
        title = article.title,
        anonymous = article.anonymous,
        content = article.content,
        author = ProfileResponse(article.author),
        createdDate = article.createdDate,
        modifiedDate = article.modifiedDate,
        hashtags = hashtags.map { it.hashtag.name },
        favorites = favorites.count { it.matchType(ArticleFavoriteType.FAVORITE) }.toLong(),
        wonders = favorites.count { it.matchType(ArticleFavoriteType.WONDER) }.toLong(),
        clips = favorites.count { it.matchType(ArticleFavoriteType.CLIP) }.toLong(),
    )
}
