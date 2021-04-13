package com.devfloor.untitled.article.application

data class GetArticleResponse(
        val option : String,
        val title: String,
        val author: Any,
        val createdDate: String,
        val content: String,
        val hashtags: List<String>,
        val favorites: Long,
        val wonders: Long,
        val clips: Long
)
