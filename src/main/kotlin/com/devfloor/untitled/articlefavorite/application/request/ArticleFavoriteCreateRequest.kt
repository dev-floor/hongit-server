package com.devfloor.untitled.articlefavorite.application.request

import com.devfloor.untitled.articlefavorite.domain.ArticleFavoriteType

data class ArticleFavoriteCreateRequest(
    val articleId: Long,
    val type: ArticleFavoriteType,
)
