package com.devfloor.hongit.core.article.domain

import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.user.domain.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByBoard(board: Board, pageRequest: PageRequest): Page<Article>
    fun findAllByAuthor(author: User, pageRequest: PageRequest): Page<Article>
    fun findAllByIdIn(ids: List<Long>, pageRequest: PageRequest): Page<Article>
    fun findTop5ByBoardOrderByCreatedAtDesc(board: Board): List<Article>
    fun findAllByAuthorAndAnonymousFalse(author: User, pageRequest: PageRequest): Page<Article>
}
