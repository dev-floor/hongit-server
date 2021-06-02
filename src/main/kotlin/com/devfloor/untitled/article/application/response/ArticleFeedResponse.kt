package com.devfloor.untitled.article.application.response

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlefavorite.domain.ArticleFavorite
import com.devfloor.untitled.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.untitled.articleoption.domain.ArticleOption
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ArticleFeedResponse(
    val id: Long,

    val options: List<String>,

    val title: String? = null,

    val anonymous: Boolean,

    val authorName: String,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val createdDate: LocalDateTime,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val modifiedDate: LocalDateTime,

    val content: String,

    val favorites: Long,

    val wonders: Long,

    val clips: Long,
) {
    constructor(
        articleOptions: List<ArticleOption>,
        article: Article,
        articleFavorites: List<ArticleFavorite>,
    ) : this(
        id = article.id,
        options = articleOptions.map { it.option.type.text },
        title = article.title,
        anonymous = article.anonymous,
        authorName = article.author.nickname,
        createdDate = article.createdDate,
        modifiedDate = article.modifiedDate,
        content = article.sliceContentByLength(CONTENT_MAX_LENGTH),
        favorites = articleFavorites.count { it.matchType(ArticleFavoriteType.FAVORITE) }.toLong(),
        wonders = articleFavorites.count { it.matchType(ArticleFavoriteType.WONDER) }.toLong(),
        clips = articleFavorites.count { it.matchType(ArticleFavoriteType.CLIP) }.toLong(),
    )

    companion object {
        private const val CONTENT_MAX_LENGTH: Long = 300
    }
}
