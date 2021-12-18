package com.devfloor.hongit.api.home.application

import com.devfloor.hongit.api.article.application.ArticleService
import com.devfloor.hongit.api.home.application.response.HomeResponse
import com.devfloor.hongit.core.board.domain.BoardRepository
import com.devfloor.hongit.core.board.domain.BoardType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HomeBoardService(
    private val boardRepository: BoardRepository,
    private val articleService: ArticleService,
) {
    @Transactional(readOnly = true)
    fun showAll(): List<HomeResponse> {
        return showTotalTopFive().plus(
            boardRepository.findAllByTypeNotOrderByIdAsc(BoardType.COURSE_BOARD)
                .map {
                    HomeResponse(
                        it.id,
                        it.title,
                        articleService.showTopFiveByBoard(it),
                    )
                }
        )
    }

    private fun showTotalTopFive(): List<HomeResponse> {
        return listOf(
            HomeResponse(
                title = "통합 좋아요",
                articles = articleService.showTopFiveByFavorite()
            ),
            HomeResponse(
                title = "통합 조회수",
                articles = articleService.showTopFiveByViewCount()
            )
        )
    }
}
