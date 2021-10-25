package com.devfloor.hongit.api.article.domain

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.board.domain.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

interface ArticleRepositoryCustom {
    fun findByFavoriteTopFive(): List<Article>

    fun findByViewCountTopFive(): List<Article>

    fun findAllByBoardOrderByViewCount(board: Board, pageable: Pageable): List<Article>

    fun findAllByBoardOrderByFavorite(board: Board, pageable: Pageable): Page<Article>

}
