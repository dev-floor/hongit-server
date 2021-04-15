package com.devfloor.untitled.article.application

data class ArticleResponse(
    val option: String,
    val title: String? = null,
    val author: Any,
    val createdDate: String,
    val content: String,
    val hashtags: List<String>,
    val favorites: Long,
    val wonders: Long,
    val clips: Long
)
