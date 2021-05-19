package com.devfloor.untitled.comment.application.request

data class CommentRequest(
    val anonymous: Boolean,
    val content: String,
)
