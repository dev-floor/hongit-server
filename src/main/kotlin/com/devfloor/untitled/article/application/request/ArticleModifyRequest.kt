package com.devfloor.untitled.article.application.request

data class ArticleModifyRequest(
    val optionIds: List<Long>,
    val title: String?,
    val content: String,
    val hashtagNames: List<String>,
)
