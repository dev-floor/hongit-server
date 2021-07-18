package com.devfloor.hongit.api.articlefavorite.application.request

import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteType

data class ArticleFavoriteCreateRequest(
    val articleId: Long,
    val type: ArticleFavoriteType,
)
