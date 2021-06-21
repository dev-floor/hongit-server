package com.devfloor.untitled.articlefavorite.application.request

data class ArticleFavoriteCreateRequest(
    val articleId: Long,
    val type: String,
)
