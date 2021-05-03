package com.devfloor.untitled.articleoption.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articleoption.domain.ArticleOption
import com.devfloor.untitled.articleoption.domain.ArticleOptionRepository
import com.devfloor.untitled.option.domain.Option
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleOptionService(
    private val repository: ArticleOptionRepository
) {
    @Transactional(readOnly = true)
    fun showAllByArticle(article: Article): List<ArticleOption> =
        repository.findAllByArticle(article)

    @Transactional
    fun createAllByOptions(article: Article, options: List<Option>) {
        options.forEach {
            repository.save(ArticleOption(article, it))
        }
    }

    @Transactional
    fun createByOption(article: Article, option: Option) {
        repository.save(ArticleOption(article, option))
    }
}
