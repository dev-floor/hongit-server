package com.devfloor.untitled.comment.application

import com.devfloor.untitled.article.application.ArticleService
import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.comment.domain.CommentRepository
import com.devfloor.untitled.common.exception.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val repository: CommentRepository,
    private val articleService: ArticleService,
) {
    @Transactional(readOnly = true)
    fun showByCommentId(commentId: Long): Comment = repository.findByIdOrNull(commentId)
        ?: throw EntityNotFoundException("존재하지 않는 댓글입니다.")

    @Transactional
    fun modifyByArticleId(articleId: Long, commentId: Long, request: CommentModifyRequest) {
        val article = articleService.showById(articleId)
        val comment = showByCommentId(commentId)
        comment.modify(Comment(article, comment.author, comment.anonymous, request.content))
    }
}
