package com.devfloor.untitled.board.application.response

import com.devfloor.untitled.article.application.response.ArticleFeedResponse

data class BoardResponse(
    val articles: List<ArticleFeedResponse>,
)
