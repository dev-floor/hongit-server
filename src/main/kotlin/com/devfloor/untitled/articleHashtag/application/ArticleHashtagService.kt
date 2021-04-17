package com.devfloor.untitled.articleHashtag.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articleHashtag.domain.ArticleHashtag
import com.devfloor.untitled.articleHashtag.domain.ArticleHashtagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleHashtagService(
    private val repository: ArticleHashtagRepository
) {
    @Transactional(readOnly = true)
    fun findAllByArticle(article: Article): List<ArticleHashtag> =
        repository.findAllByArticle(article) ?: emptyList()
}
