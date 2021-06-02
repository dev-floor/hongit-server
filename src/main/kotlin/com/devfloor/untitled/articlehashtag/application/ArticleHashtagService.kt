package com.devfloor.untitled.articlehashtag.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtagRepository
import com.devfloor.untitled.hashtag.domain.Hashtag
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleHashtagService(
    private val articleHashtagRepository: ArticleHashtagRepository,
) {
    fun findAllByArticle(article: Article): List<ArticleHashtag> =
        articleHashtagRepository.findAllByArticle(article)

    fun save(articleHashtag: ArticleHashtag): ArticleHashtag =
        articleHashtagRepository.save(articleHashtag)

    fun saveAll(articleHashtags: List<ArticleHashtag>): List<ArticleHashtag> =
        articleHashtagRepository.saveAll(articleHashtags)

    @Transactional
    fun modifyByArticle(article: Article, hashtags: List<Hashtag>) {
        articleHashtagRepository.deleteAllByArticle(article)
        hashtags.map { ArticleHashtag(article, it) }
            .let { articleHashtagRepository.saveAll(it) }
    }

    fun deleteAllByArticle(article: Article) = articleHashtagRepository.deleteAllByArticle(article)
}
