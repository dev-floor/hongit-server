package com.devfloor.untitled.comment.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface CommentRepository : JpaRepository<Comment, Long> {

    fun findAllByArticle(article: Article): List<Comment>
}
