package com.devfloor.untitled.articleoption.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articleoption.domain.ArticleOption
import com.devfloor.untitled.articleoption.domain.ArticleOptionRepository
import com.devfloor.untitled.option.domain.Option
import org.springframework.stereotype.Service

@Service
class ArticleOptionService(
    private val repository: ArticleOptionRepository,
) {
    fun findAllByArticle(article: Article): List<ArticleOption> =
        repository.findAllByArticle(article)

    fun saveAll(article: Article, options: List<Option>): List<ArticleOption> =
        options.map { ArticleOption(article, it) }
            .let { repository.saveAll(it) }

    fun modifyByArticle(article: Article, options: List<Option>) {
        repository.deleteAllByArticle(article)
        saveAll(article, options)
    }

    fun deleteAllByArticle(article: Article) = repository.deleteAllByArticle(article)
}
