package com.devfloor.hongit.core.comment.domain

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByArticle(article: Article): List<Comment>

    fun findAllByAuthor(author: User): List<Comment>
}
