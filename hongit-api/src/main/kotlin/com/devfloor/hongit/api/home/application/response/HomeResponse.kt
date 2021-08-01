package com.devfloor.hongit.api.home.application.response

import com.devfloor.hongit.api.article.application.response.ArticleHomeResponse

data class HomeResponse(
    val boardId: Long,

    val title: String,

    val articles: List<ArticleHomeResponse>,
)
