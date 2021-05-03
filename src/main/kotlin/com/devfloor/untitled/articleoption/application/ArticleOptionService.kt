package com.devfloor.untitled.articleoption.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articleoption.domain.ArticleOption
import com.devfloor.untitled.articleoption.domain.ArticleOptionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleOptionService(
    private val repository: ArticleOptionRepository
) {
    @Transactional(readOnly = true)
    fun showAllByArticle(article: Article): List<ArticleOption> {
        return repository.findAllByArticle(article)
    }
}
