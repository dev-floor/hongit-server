package com.devfloor.hongit.core.comment.domain

import com.devfloor.hongit.core.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByArticle(article: Article): List<Comment>
}
