package com.devfloor.hongit.api.board.presentation

import com.devfloor.hongit.api.article.application.ArticleService
import com.devfloor.hongit.api.article.application.response.ArticleFeedResponse
import com.devfloor.hongit.api.board.application.BoardService
import com.devfloor.hongit.api.board.application.response.BoardResponse
import com.devfloor.hongit.api.board.application.response.BoardSimpleResponse
import com.devfloor.hongit.api.board.presentation.BoardController.Companion.BOARD_API_URI
import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.security.core.LoginUser
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import com.devfloor.hongit.core.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping(value = [BOARD_API_URI])
class BoardController(
    private val boardService: BoardService,
    private val articleService: ArticleService,
) {
    /**
     * 스크린: 게시판 > 게시글 목록 조회
     */
    @GetMapping(params = ["boardId"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByBoardId(
        @RequestParam boardId: Long,
        @RequestParam(required = false) sort: String?,
        @RequestParam(required = false) options: List<Long>?,
        @RequestParam size: Int
    ): List<ArticleFeedResponse> =
        articleService.showAllByBoardId(boardId, size)
            .also { log.info("boardId = $boardId, sort = $sort, options = $options") }

    /**
     * 스크린: 프로필 & 마이페이지 > 작성한 게시글 목록 조회
     * 로그인 본인의 경우 익명글 포함
     */
    @GetMapping(params = ["authorId"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByUserId(
        @LoginUser loginUser: User,
        @RequestParam authorId: Long,
        @RequestParam size: Int,
    ): List<ArticleFeedResponse> {
        return if (loginUser.id == authorId) {
            articleService.showAllByUserId(authorId, size)
                .also { log.info("userId = $authorId") }
        } else {
            articleService.showAllByUserIdNotAnonymous(authorId, size)
                .also { log.info("userId = $authorId") }
        }
    }

    /**
     * 스크린: 프로필 & 마이페이지 > 좋아요한 게시글 목록 조회
     */
    @GetMapping(params = ["favoritedUserId", "favoriteType"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByFavoritedUserId(
        @LoginUser loginUser: User,
        @RequestParam(value = "favoritedUserId") userId: Long,
        @RequestParam(value = "favoriteType") type: String,
        @RequestParam size: Int,
    ): List<ArticleFeedResponse> {
        return articleService.showAllByFavoritedUserId(userId, size)
            .also { log.info("userId = $userId, type = $type") }
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    fun showAll(): List<BoardSimpleResponse> {
        log.info("[BoardController.showAll] 게시판 목록 조회 - url: $BOARD_API_URI")
        return boardService.showAll()
            .also { log.info("[BoardController.showAll] 게시판 목록 조회 완료 - response: $it") }
    }

    @GetMapping(params = ["type"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByBoardType(@RequestParam type: BoardType): List<BoardResponse> {
        log.info("[BoardController.showAllBoardByBoardType] 수업 게시판 선택 화면 조회 - type: $type")

        return boardService.showAllBoardByBoardType(type)
            .also { log.info("[BoardController.showAllBoardByBoardType] 수업 게시판 선택 화면 조회 완료 - response: $it") }
    }

    companion object {
        const val BOARD_API_URI = "$BASE_API_URI/boards"
    }
}
