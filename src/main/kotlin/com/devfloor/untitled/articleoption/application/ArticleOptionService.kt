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
    fun create(article: Article, optionList: List<Option>): List<ArticleOption> {
        val articleOptionList = mutableListOf<ArticleOption>()
        for(index in optionList.indices){
            articleOptionList.add(repository.save(toArticleOption(article, optionList[index])))
        }

        return articleOptionList
    }

    fun toArticleOption(article: Article, option: Option): ArticleOption {
        return ArticleOption(article, option)
    }
}
