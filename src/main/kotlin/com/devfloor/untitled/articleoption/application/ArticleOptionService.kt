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
    @Transactional
    fun modifyByArticle(article: Article, options: List<Option>) {
        articleOptionRepository.deleteAllByArticle(article)
        options.map { ArticleOption(article, it) }
            .let { articleOptionRepository.saveAll(it) }
    }
}
