package com.devfloor.untitled.home.application.response

import com.devfloor.untitled.article.application.response.ArticleHomeResponse

data class HomeResponse(
    val boardId: Long,

    val title: String,

    val articles: List<ArticleHomeResponse>,
) {

}
