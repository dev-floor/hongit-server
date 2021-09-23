package com.devfloor.hongit.api.article.domain

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.board.domain.Board
import org.springframework.data.domain.PageRequest

interface ArticleRepositoryCustom {
    fun findByFavoriteTopFive(): List<Article>
    fun findByViewCountTopFive(): List<Article>
    fun findAllByBoardOrderByViewCount(board: Board, pageRequest: PageRequest): List<Article>
    fun findAllByBoardOrderByFavorite(board: Board, pageRequest: PageRequest): List<Article>
}
