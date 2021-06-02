package com.devfloor.untitled.articleoption.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articleoption.domain.ArticleOption
import com.devfloor.untitled.articleoption.domain.ArticleOptionRepository
import com.devfloor.untitled.option.domain.Option
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleOptionService(
    private val articleOptionRepository: ArticleOptionRepository,
) {
    fun findAllByArticle(article: Article): List<ArticleOption> =
        articleOptionRepository.findAllByArticle(article)

    fun saveAll(article: Article, options: List<Option>): List<ArticleOption> =
        options.map { ArticleOption(article, it) }
            .let { articleOptionRepository.saveAll(it) }

    @Transactional
    fun modifyByArticle(article: Article, options: List<Option>) {
        articleOptionRepository.deleteAllByArticle(article)
        saveAll(article, options)
    }

    fun deleteAllByArticle(article: Article) = articleOptionRepository.deleteAllByArticle(article)
}
