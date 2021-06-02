package com.devfloor.untitled.articlehashtag.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtagRepository
import com.devfloor.untitled.hashtag.domain.Hashtag
import org.springframework.stereotype.Service

@Service
class ArticleHashtagService(
    private val repository: ArticleHashtagRepository,
) {
    fun showAllByArticle(article: Article): List<ArticleHashtag> =
        repository.findAllByArticle(article)

    fun create(articleHashtag: ArticleHashtag) = repository.save(articleHashtag)

    fun modifyByArticle(article: Article, hashtags: List<Hashtag>) {
        repository.deleteAllByArticle(article)
        hashtags.map { ArticleHashtag(article, it) }
            .let { repository.saveAll(it) }
    }

    fun destroyAllByArticle(article: Article) = repository.deleteAllByArticle(article)
}
