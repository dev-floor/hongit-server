package com.devfloor.untitled.comment.application.request

data class CommentRequest(
    val articleId: Long,
    val anonymous: Boolean,
    val content: String,
)
