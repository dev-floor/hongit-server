package com.devfloor.untitled.articlehashtag.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtagRepository
import com.devfloor.untitled.hashtag.application.HashtagService
import com.devfloor.untitled.hashtag.domain.Hashtag
import org.springframework.stereotype.Service

@Service
class ArticleHashtagService(
    private val repository: ArticleHashtagRepository,
    private val hashtagService: HashtagService,
) {
    fun showAllByArticle(article: Article): List<ArticleHashtag> =
        repository.findAllByArticle(article)

//    fun createAll(articleHashtags: List<ArticleHashtag>) {
//        repository.saveAll(articleHashtags)
//    }

    fun createAll(article: Article, hashtags: List<String>) {
        hashtags.map { hashtagService.createByName(it) }
            .map { ArticleHashtag(article, it) }
            .let { repository.saveAll(it) }
    }

//    fun create(articleHashtag: ArticleHashtag) {
//        repository.save(articleHashtag)
//    }

    fun destroyAllByArticle(article: Article) = repository.deleteAllByArticle(article)

//    fun existsById(id: Long): Boolean = repository.existsById(id)

    fun modifyByArticle(article: Article, hashtags: List<String>) {
        destroyAllByArticle(article)
        createAll(article, hashtags)
    }
}
