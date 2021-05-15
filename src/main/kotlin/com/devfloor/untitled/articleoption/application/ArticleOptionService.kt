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
    fun showAllByArticle(article: Article): List<ArticleOption> =
        repository.findAllByArticle(article)

    fun modifyByArticle(article: Article, optionsRequest: List<String>) {
        destroyAllByArticle(article)

        // TODO: 게시글 생성 merge 이후 articleOption 생성 로직 추가 예정
    }

    private fun destroyAllByArticle(article: Article) = repository.deleteAllByArticle(article)
}
