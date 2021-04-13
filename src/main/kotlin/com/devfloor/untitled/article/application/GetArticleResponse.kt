package com.devfloor.untitled.article.application

data class GetUserResponse(
        val option : String,
        val title: String,
        val author: Any,
        val createdDate: String,
        val content: String,
        val hashtags: Any, // TODO: entity 구현
        val favorites: Long,
        val wonders: Long,
        val clips: Long
)
