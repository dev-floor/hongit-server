package com.devfloor.untitled.comment.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.comment.domain.CommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
) {
    @Transactional(readOnly = true)
    fun showAllByArticle(article: Article) = commentRepository.findAllByArticle(article)
}
