package com.devfloor.untitled.article.presentation

import com.devfloor.untitled.article.application.ArticleService
import com.devfloor.untitled.article.application.request.ArticleCreateRequest
import com.devfloor.untitled.article.application.request.ArticleModifyRequest
import com.devfloor.untitled.article.application.response.ArticleFeedResponse
import com.devfloor.untitled.article.application.response.ArticleResponse
import com.devfloor.untitled.user.domain.User
import com.devfloor.untitled.user.domain.UserType
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(
    private val articleService: ArticleService,
) {
    @GetMapping(value = ["/articles/{articleId}"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showByArticleId(@PathVariable articleId: Long): ArticleResponse =
        articleService.showByArticleId(articleId)

    @GetMapping(value = ["/articles"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByBoardId(@RequestParam boardId: Long): List<ArticleFeedResponse> =
        articleService.showAllFeedsByBoardId(boardId)

    @PostMapping(value = ["/articles"])
    @ResponseStatus(value = HttpStatus.CREATED)
    fun create(@RequestBody request: ArticleCreateRequest): Long {
        val user = User(
            nickname = "userNicknameData",
            type = UserType.STUDENT,
            image = "userImageData",
            classOf = "userClassOfData",
            github = "userGithubData",
            blog = "userBlogData",
            description = "userDescriptionData"
        )
        return articleService.create(request, user)
    }

    @PutMapping(value = ["/articles/{articleId}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun modifyByArticleId(
        @PathVariable articleId: Long,
        @RequestBody request: ArticleModifyRequest,
    ) = articleService.modifyByArticleId(articleId, request)

    @DeleteMapping(value = ["/articles/{articleId}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun destroyByArticleId(@PathVariable articleId: Long) =
        articleService.destroyByArticleId(articleId)
}
