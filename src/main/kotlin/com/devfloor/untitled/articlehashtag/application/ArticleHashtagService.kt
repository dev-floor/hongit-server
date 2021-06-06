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
    @Transactional
    fun modifyByArticle(article: Article, hashtags: List<Hashtag>) {
        articleHashtagRepository.deleteAllByArticle(article)
        hashtags.map { ArticleHashtag(article, it) }
            .let { articleHashtagRepository.saveAll(it) }
    }
}
