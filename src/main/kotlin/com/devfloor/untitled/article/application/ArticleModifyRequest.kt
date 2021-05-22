package com.devfloor.untitled.article.application

data class ArticleModifyRequest(
    val options: List<String>,
    val title: String?,
    val content: String,
    val hashtags: List<String>,
)
