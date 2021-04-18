package com.devfloor.untitled.article.application

data class ArticleResponse(
    val options: List<String>,
    val title: String? = null,
    val author: Any,
    val createdDate: String,
    val modifiedDate: String,
    val content: String,
    val hashtags: List<String>,
    val favorites: Long,
    val wonders: Long,
    val clips: Long
)
