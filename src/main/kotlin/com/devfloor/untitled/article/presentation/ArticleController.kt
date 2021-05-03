package com.devfloor.untitled.article.presentation

import com.devfloor.untitled.article.application.ArticleFacade
import com.devfloor.untitled.article.application.ArticleRequest
import com.devfloor.untitled.article.application.ArticleResponse
import com.devfloor.untitled.user.domain.User
import com.devfloor.untitled.user.domain.UserType
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class ArticleController(
    private val articleFacade: ArticleFacade
) {
    @GetMapping(value = ["/articles/{id}"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showById(@PathVariable id: Long): ArticleResponse =
        articleFacade.showByArticleId(id)

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
}
