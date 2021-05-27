package com.devfloor.untitled.article.presentation

import com.devfloor.untitled.article.application.request.ArticleModifyRequest
import com.devfloor.untitled.article.application.request.ArticleRequest
import com.devfloor.untitled.article.application.response.ArticleResponse
import com.devfloor.untitled.article.application.ArticleService
import com.devfloor.untitled.user.domain.User
import com.devfloor.untitled.user.domain.UserType
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(
    private val articleService: ArticleService,
) {
    @GetMapping(value = ["/articles/{articleId}"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showById(@PathVariable articleId: Long): ArticleResponse =
        articleService.showByArticleId(articleId)

    @PostMapping(value = ["/articles"])
    @ResponseStatus(value = HttpStatus.CREATED)
    fun create(@RequestBody request: ArticleRequest): Long {
        val user = User(
            "userNicknameData",
            UserType.STUDENT,
            "userImageData",
            "userClassOfData",
            "userGithubData",
            "userBlogData",
            "userDescriptionData"
        )
        return articleService.create(request, user)
    }

    @PutMapping(value = ["/articles/{articleId}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun modifyById(
        @PathVariable articleId: Long,
        @RequestBody request: ArticleModifyRequest,
    ) {
        return articleService.modifyByArticleId(articleId, request)
    }

    @DeleteMapping(value = ["/articles/{articleId}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun destroyById(@PathVariable articleId: Long) = articleService.destroyById(articleId)
}
