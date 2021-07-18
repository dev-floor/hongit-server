package com.devfloor.hongit.api.article.application.request

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.user.domain.User

data class ArticleCreateRequest(
    val optionIds: List<Long>,
    val title: String?,
    val anonymous: Boolean,
    val content: String,
    val hashtagNames: List<String>,
    val boardId: Long,
) {
    fun toArticle(author: User, board: Board): Article =
        Article(
            title = this.title,
            anonymous = this.anonymous,
            content = this.content,
            author = author,
            board = board,
        )
}
