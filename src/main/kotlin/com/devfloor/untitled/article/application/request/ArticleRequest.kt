package com.devfloor.untitled.article.application.request

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.user.domain.User

data class ArticleRequest(
    val optionIds: List<Long>,
    val title: String?,
    val anonymous: Boolean,
    val content: String,
    val hashtags: List<String>,
    val boardId: Long,
) {
    val hasOptions get() = optionIds.isNotEmpty()

    fun toArticle(author: User, board: Board): Article =
        Article(
            title = this.title,
            anonymous = this.anonymous,
            content = this.content,
            author = author,
            board = board
        )
}
