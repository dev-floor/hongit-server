package com.devfloor.untitled.comment.application

import com.devfloor.untitled.article.application.ArticleService
import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.comment.application.request.CommentRequest
import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.comment.domain.CommentRepository
import com.devfloor.untitled.user.domain.User
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val repository: CommentRepository,
    private val articleService: ArticleService,
) {
    fun showAllByArticle(article: Article): List<Comment> {
        return repository.findAllByArticle(article)
    }

    fun create(
        articleId: Long,
        author: User,
        request: CommentRequest,
    ): Comment {
        val article = articleService.showById(articleId)
        return Comment(article, author, request.anonymous, request.content)
            .let { repository.save(it) }
    }
}
