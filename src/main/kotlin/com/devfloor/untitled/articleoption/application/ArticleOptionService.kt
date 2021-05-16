package com.devfloor.untitled.articleoption.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articleoption.domain.ArticleOption
import com.devfloor.untitled.articleoption.domain.ArticleOptionRepository
import org.springframework.stereotype.Service

@Service
class ArticleOptionService(
    private val repository: ArticleOptionRepository
) {
    fun showAllByArticle(article: Article): List<ArticleOption> =
        repository.findAllByArticle(article)

    fun createAll(articleOptions: List<ArticleOption>) {
        repository.saveAll(articleOptions)
    }

    fun create(articleOption: ArticleOption) {
        repository.save(articleOption)
    }

    fun modifyByArticle(article: Article, optionsRequest: List<String>) {
        destroyAllByArticle(article)

        // TODO: 게시글 생성 merge 이후 articleOption 생성 로직 추가 예정
    }

    private fun destroyAllByArticle(article: Article) = repository.deleteAllByArticle(article)
}
