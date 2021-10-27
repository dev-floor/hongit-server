package com.devfloor.hongit.core.article.domain

import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.user.domain.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByBoard(board: Board, pageable: Pageable): Page<Article>

    fun countAllByBoard(board: Board): Int

    fun findAllByAuthor(author: User, pageRequest: Pageable): Page<Article>

    fun countAllByAuthor(author: User): Int

    fun findAllByIdIn(ids: List<Long>, pageRequest: Pageable): Page<Article>

    fun countAllByIdIn(ids: List<Long>): Int

    fun findTop5ByBoardOrderByCreatedAtDesc(board: Board): List<Article>
}
