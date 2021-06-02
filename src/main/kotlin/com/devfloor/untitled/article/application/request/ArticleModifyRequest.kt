package com.devfloor.untitled.article.application.request

data class ArticleModifyRequest(
    val options: List<String>,
    val title: String?,
    val content: String,
    val hashtags: List<String>,
)
