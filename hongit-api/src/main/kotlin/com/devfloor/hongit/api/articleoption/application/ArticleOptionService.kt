package com.devfloor.hongit.api.articleoption.application

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.articleoption.domain.ArticleOption
import com.devfloor.hongit.core.articleoption.domain.ArticleOptionRepository
import com.devfloor.hongit.core.option.domain.Option
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
