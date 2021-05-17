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

    fun createAll(articleOptions: List<ArticleOption>) {
        repository.saveAll(articleOptions)
    }

//    fun create(articleOption: ArticleOption) {
//        repository.save(articleOption)
//    }

//    fun createAll(article: Article, options: List<String>) {
//        optionService.showAllByOptionType(options)
//            .map { ArticleOption(article, it) }
//            .let { repository.saveAll(it) }
//    }

    fun modifyByArticle(article: Article, options: List<String>) {
        destroyAllByArticle(article)
        optionService.showAllByOptionType(options) // TODO : 위의 createAll 메서드 사용예정
            .map { ArticleOption(article, it) }
            .let { repository.saveAll(it) }
    }

    private fun destroyAllByArticle(article: Article) = repository.deleteAllByArticle(article)
}
