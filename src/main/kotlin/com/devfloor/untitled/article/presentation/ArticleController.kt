package com.devfloor.untitled.article.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController {

    @GetMapping("/articles/{id}")
    fun article(@PathVariable id: String) {
        TODO()
    }
}