package com.devfloor.untitled.articlehashtag.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleHashtagService(
    private val repository: ArticleHashtagRepository
) {
    @Transactional(readOnly = true)
    fun showAllByArticle(article: Article): List<ArticleHashtag> =
        repository.findAllByArticle(article)

    fun destroyAllByArticle(article: Article) = repository.deleteAllByArticle(article)

    fun existsById(id: Long): Boolean = repository.existsById(id)
}
