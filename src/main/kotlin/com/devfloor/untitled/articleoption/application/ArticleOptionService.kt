package com.devfloor.untitled.articleoption.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articleoption.domain.ArticleOption
import com.devfloor.untitled.articleoption.domain.ArticleOptionRepository
import com.devfloor.untitled.option.application.OptionService
import org.springframework.stereotype.Service

@Service
class ArticleOptionService(
    private val repository: ArticleOptionRepository,
    private val optionService: OptionService,
) {
    fun showAllByArticle(article: Article): List<ArticleOption> =
        repository.findAllByArticle(article)

    fun createAll(article: Article, options: List<String>) {
        optionService.showAllByOptionType(options)
            .map { ArticleOption(article, it) }
            .let { repository.saveAll(it) }
    }

    fun modifyByArticle(article: Article, options: List<String>) {
        repository.deleteAllByArticle(article)
        createAll(article, options)
    }
}
