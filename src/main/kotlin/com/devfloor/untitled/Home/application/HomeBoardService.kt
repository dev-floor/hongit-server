package com.devfloor.untitled.Home.application

import com.devfloor.untitled.Home.application.response.HomeResponse
import com.devfloor.untitled.Home.domain.HomeBoardRepository
import com.devfloor.untitled.Home.domain.HomeBoardType
import com.devfloor.untitled.article.application.ArticleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HomeBoardService(
    private val homeBoardRepository: HomeBoardRepository,
    private val articleService: ArticleService,
) {
    @Transactional(readOnly = true)
    fun showAll(): List<HomeResponse> {
        return homeBoardRepository.findAll()
            .map { homeBoard ->
                val articles =
                    when {
                        // TODO : 조회수순, 좋아요순 5개 게시글 추출 구현 필요
                        homeBoard.matchType(HomeBoardType.TOTAL_VIEW) -> articleService.showTop5ByBoard(homeBoard.board)
                        homeBoard.matchType(HomeBoardType.TOTAL_VIEW) -> articleService.showTop5ByBoard(homeBoard.board)
                        else -> articleService.showTop5ByBoard(homeBoard.board)
                    }

                HomeResponse(
                    articles,
                    homeBoard
                )
            }
    }
}
