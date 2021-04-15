package com.devfloor.untitled.article.presentation

import com.devfloor.untitled.article.application.ArticleFacade
import com.devfloor.untitled.article.application.ArticleResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(
    private val articleFacade: ArticleFacade
) {
    @GetMapping("/articles/{article_id}")
    fun getArticle(@PathVariable id: Long): ResponseEntity<ArticleResponse> =
        try {
            ResponseEntity(articleFacade.getArticle(id), HttpStatus.OK)
        } catch (e: IllegalArgumentException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
}
