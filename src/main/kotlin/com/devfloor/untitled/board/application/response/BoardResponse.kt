package com.devfloor.untitled.board.application.response

import com.devfloor.untitled.article.application.response.ArticleFeedResponse

data class BoardResponse(
    val articles: List<ArticleFeedResponse>,
    // TODO() : 스크린 구성 이후 필드 추가
)
