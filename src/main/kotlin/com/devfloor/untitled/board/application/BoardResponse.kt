package com.devfloor.untitled.board.application

import com.devfloor.untitled.article.application.ArticlesResponse

data class BoardResponse(
    val articles: List<ArticlesResponse>,
    // TODO() : 스크린 구성 이후 필드 추가
)
