package com.devfloor.untitled.article.presentation

import com.devfloor.untitled.article.application.ArticleFacade
import com.devfloor.untitled.article.application.ArticleRequest
import com.devfloor.untitled.article.application.ArticleResponse
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
    @ResponseStatus(value = HttpStatus.OK)
    fun create(@RequestBody articleRequest: ArticleRequest) =
        articleFacade.create(articleRequest)
}
