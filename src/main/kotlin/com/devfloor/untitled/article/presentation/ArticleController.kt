package com.devfloor.untitled.article.presentation

import com.devfloor.untitled.article.application.ArticleService
import com.devfloor.untitled.article.application.request.ArticleCreateRequest
import com.devfloor.untitled.article.application.request.ArticleModifyRequest
import com.devfloor.untitled.article.application.response.ArticleFeedResponse
import com.devfloor.untitled.article.application.response.ArticleResponse
import com.devfloor.untitled.article.presentation.ArticleController.Companion.ARTICLE_API_URI
import com.devfloor.untitled.common.config.auth.LoginUser
import com.devfloor.untitled.common.utils.BASE_API_URI
import com.devfloor.untitled.user.domain.User
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

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByBoardId(@RequestParam boardId: Long): List<ArticleFeedResponse> =
        articleService.showAllByBoardId(boardId)

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
