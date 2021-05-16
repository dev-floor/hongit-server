package com.devfloor.untitled.article.presentation

import com.devfloor.untitled.article.application.ArticleFacade
import com.devfloor.untitled.article.application.ArticleRequest
import com.devfloor.untitled.article.application.ArticleModifyRequest
import com.devfloor.untitled.article.application.ArticleResponse
import com.devfloor.untitled.user.domain.User
import com.devfloor.untitled.user.domain.UserType
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(
    private val articleFacade: ArticleFacade
) {
    @GetMapping(value = ["/articles/{id}"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showById(@PathVariable id: Long): ArticleResponse = articleFacade.showByArticleId(id)

    @PostMapping(value = ["/articles"])
    @ResponseStatus(value = HttpStatus.CREATED)
    fun create(@RequestBody articleRequest: ArticleRequest): Long {
        val user = User(
            "userNicknameData",
            UserType.STUDENT,
            "userImageData",
            "userClassOfData",
            "userGithubData",
            "userBlogData",
            "userDescriptionData"
        )
        return articleFacade.create(articleRequest, user)
    }

    @PutMapping(value = ["/articles/{id}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun modifyById(
        @PathVariable id: Long,
        @RequestBody modifyRequest: ArticleModifyRequest,
    ) = articleFacade.modifyByArticleId(id, modifyRequest)
}
