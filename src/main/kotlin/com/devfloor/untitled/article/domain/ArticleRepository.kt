package com.devfloor.untitled.article.domain

import com.devfloor.untitled.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByBoard(board: Board): List<Article>
}
