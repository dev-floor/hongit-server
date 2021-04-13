package com.devfloor.untitled.articleHashtag.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articleHashtag.domain.ArticleHashtagRepository

class ArticleHashtagService(
    private val repository: ArticleHashtagRepository
) {

    fun findByArticle(article: Article): List<Article>? = repository.findAllByArticle(article)
}