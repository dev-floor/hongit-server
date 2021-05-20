package com.devfloor.untitled.comment.application

data class CommentModifyRequest(
    val content: String,
) {
    fun showContent(): String {
        return this.content
    }
}
