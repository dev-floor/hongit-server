package com.devfloor.untitled.Home.application.response

import com.devfloor.untitled.Home.domain.HomeBoard
import com.devfloor.untitled.article.application.response.ArticleHomeResponse

data class HomeResponse(
    val boardId: Long? = null,

    val title: String,

    val articles: List<ArticleHomeResponse>,
) {
    constructor(
        articles: List<ArticleHomeResponse>,
        homeBoard: HomeBoard,
    ) : this(
        boardId = homeBoard.board.id,
        title = homeBoard.board.title,
        articles = articles,
    )
}
