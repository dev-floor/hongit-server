package com.devfloor.hongit.api.article.presentation

import com.devfloor.hongit.api.article.application.ArticleService
import com.devfloor.hongit.api.article.application.request.ArticleCreateRequest
import com.devfloor.hongit.api.article.application.request.ArticleModifyRequest
import com.devfloor.hongit.api.article.application.response.ArticleFeedResponse
import com.devfloor.hongit.api.article.application.response.ArticleResponse
import com.devfloor.hongit.api.article.domain.ArticleSortType
import com.devfloor.hongit.api.article.presentation.ArticleController.Companion.ARTICLE_API_URI
import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.security.core.LoginUser
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import com.devfloor.hongit.core.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping(value = [ARTICLE_API_URI])
class ArticleController(
    private val articleService: ArticleService,
) {
    @GetMapping(value = ["/{articleId}"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showByArticleId(@PathVariable articleId: Long): ArticleResponse = articleService.showByArticleId(articleId)

    @GetMapping(params = ["userId"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByUserId(
        @RequestParam userId: Long,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "1") range: Int,
    ): List<ArticleFeedResponse> =
        articleService.showAllByUserId(userId, page, range)

    /**
     * 스크린: 게시판 > 게시글 목록 조회
     */
    @GetMapping(params = ["boardId"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByBoardId(
        @RequestParam boardId: Long,
        @RequestParam(required = false) sort: ArticleSortType?,
        @RequestParam(required = false) options: List<Long>?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "1") range: Int,
    ): List<ArticleFeedResponse> =
        articleService.showAllByBoardId(boardId, page, range, sort, options)
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
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "1") range: Int,
    ): List<ArticleFeedResponse> {
        return if (loginUser.id == authorId) {
            articleService.showAllByUserId(authorId, page, range)
                .also { log.info("userId = $authorId") }
        } else {
            articleService.showAllByUserIdNotAnonymous(authorId, page, range)
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
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "1") range: Int,
    ): List<ArticleFeedResponse> {
        return articleService.showAllByFavoritedUserId(userId, page, range)
            .also { log.info("userId = $userId, type = $type") }
    }

    @PostMapping
    fun create(@RequestBody request: ArticleCreateRequest, @LoginUser author: User): ResponseEntity<Unit> {
        val articleId = articleService.create(request, author)
        return ResponseEntity.created(URI.create("$ARTICLE_API_URI/$articleId")).build()
    }

    @PutMapping(value = ["/{articleId}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun modifyByArticleId(
        @PathVariable articleId: Long,
        @RequestBody request: ArticleModifyRequest,
        @LoginUser loginUser: User,
    ) = articleService.modifyByArticleId(articleId, request)

    @DeleteMapping(value = ["/{articleId}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun destroyByArticleId(
        @PathVariable articleId: Long,
        @LoginUser loginUser: User,
    ) = articleService.destroyByArticleId(articleId)

    companion object {
        const val ARTICLE_API_URI = "$BASE_API_URI/articles"
    }
}
