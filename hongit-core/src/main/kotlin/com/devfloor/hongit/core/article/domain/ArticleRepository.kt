package com.devfloor.hongit.core.article.domain

import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByBoard(board: Board): List<Article>
    fun findAllByAuthor(author: User): List<Article>
    fun findAllByIdIn(ids: List<Long>): List<Article>
    fun findTop5ByBoardOrderByCreatedAtDesc(board: Board): List<Article>
    fun findAllByAuthorAndAnonymousFalse(author: User): List<Article>
}
