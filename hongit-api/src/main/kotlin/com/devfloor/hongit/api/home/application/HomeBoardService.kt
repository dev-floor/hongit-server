package com.devfloor.hongit.api.home.application

import com.devfloor.hongit.api.article.application.ArticleService
import com.devfloor.hongit.core.board.domain.BoardRepository
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.api.home.application.response.HomeResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HomeBoardService(
    private val boardRepository: BoardRepository,
    private val articleService: ArticleService,
) {
    @Transactional(readOnly = true)
    fun showAll(): List<HomeResponse> {
        return boardRepository.findByTypeNot(BoardType.COURSE_BOARD)
            .map { board ->
                val articles = articleService.showTopFiveByBoard(board)

                HomeResponse(
                    board.id,
                    board.title,
                    articles,
                )
            }.plus(
                articleService.showTopFiveByFavorite().let {
                    HomeResponse(
                        boardId = -1,
                        title = "통합 좋아요",
                        it
                    )
                }
            ).plus(
                articleService.showTopFiveByViewCount().let {
                    HomeResponse(
                        boardId = -1,
                        title = "통합 조회수",
                        it
                    )
                }
            )
    }
}
