package com.devfloor.untitled.articlehashtag.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtagRepository
import org.springframework.stereotype.Service

@Service
class ArticleHashtagService(
    private val repository: ArticleHashtagRepository
) {
    fun showAllByArticle(article: Article): List<ArticleHashtag> =
        repository.findAllByArticle(article)

    fun createAll(articleHashtags: List<ArticleHashtag>) {
        repository.saveAll(articleHashtags)
    }

    fun create(articleHashtag: ArticleHashtag) {
        repository.save(articleHashtag)
    }
}
