package com.devfloor.untitled.article.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.user.domain.User


data class ArticleRequest(
    val options: List<String>,
    val title: String? = null,
    val anonymous: Boolean,
    val content: String,
    val hashtags: List<String>
) {
    fun toArticleWithUser(articleRequest: ArticleRequest, user: User): Article {
        return Article(articleRequest.title, articleRequest.anonymous, articleRequest.content, user)
    }
}


