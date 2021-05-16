package com.devfloor.untitled.comment.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.comment.domain.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
) {
    fun showAllByArticle(article: Article): List<Comment> {
        return commentRepository.findAllByArticle(article)
    }
}
