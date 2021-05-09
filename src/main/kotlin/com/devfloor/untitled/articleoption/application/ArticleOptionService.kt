package com.devfloor.untitled.articleoption.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articleoption.domain.ArticleOption
import com.devfloor.untitled.articleoption.domain.ArticleOptionRepository
import org.springframework.stereotype.Service

@Service
class ArticleOptionService(
    private val repository: ArticleOptionRepository
) {
    fun showAllByArticle(article: Article): List<ArticleOption> =
        repository.findAllByArticle(article)
}
