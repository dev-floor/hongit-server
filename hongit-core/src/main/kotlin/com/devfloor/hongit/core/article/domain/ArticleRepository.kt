package com.devfloor.hongit.core.article.domain

import com.devfloor.hongit.core.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByBoard(board: Board): List<Article>
}
