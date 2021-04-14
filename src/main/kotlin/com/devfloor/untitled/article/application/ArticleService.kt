package com.devfloor.untitled.article.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.article.domain.ArticleRepository
import org.springframework.data.repository.findByIdOrNull
import java.lang.IllegalArgumentException

class ArticleService(
        private val repository: ArticleRepository
) {

    fun findById(id: Long): Article = repository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Not found")
}
