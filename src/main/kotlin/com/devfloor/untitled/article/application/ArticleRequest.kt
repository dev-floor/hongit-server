package com.devfloor.untitled.article.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.user.domain.User

data class ArticleRequest(
    val options: List<String>,
    val title: String?,
    val anonymous: Boolean,
    val content: String,
    val hashtags: List<String>,
    val board: Board, // TODO: 2021/06/01 boardId로 수정해야 함
) {
    val hasOptions get() = options.isNotEmpty()

    fun toArticle(author: User): Article =
        Article(
            title = this.title,
            anonymous = this.anonymous,
            content = this.content,
            author = author,
            board = this.board
        )
}
