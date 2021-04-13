package com.devfloor.untitled.article.application

import com.devfloor.untitled.article.domain.ArticleRepository

class ArticleService(
        private val repository: ArticleRepository
) {

    fun getArticle(id: Long) = repository.findById(id)
}
