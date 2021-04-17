package com.devfloor.untitled.articleOption.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articleOption.domain.ArticleOption
import com.devfloor.untitled.articleOption.domain.ArticleOptionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleOptionService(
    private val repository: ArticleOptionRepository
) {
    @Transactional(readOnly = true)
    fun findAllByArticle(article: Article): List<ArticleOption> =
        repository.findAllByArticle(article) ?: emptyList()
}