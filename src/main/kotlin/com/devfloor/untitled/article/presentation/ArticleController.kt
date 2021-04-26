package com.devfloor.untitled.article.presentation

import com.devfloor.untitled.article.application.ArticleFacade
import com.devfloor.untitled.article.application.ShowAllResponse
import com.devfloor.untitled.article.application.ShowResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(
    private val articleFacade: ArticleFacade
) {
    @GetMapping(value = ["/articles/{id}"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showById(@PathVariable id: Long): ShowResponse =
        articleFacade.showByArticleId(id)

    @GetMapping(value = ["/articles"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showAll(): List<ShowAllResponse> = articleFacade.showAll()
}
