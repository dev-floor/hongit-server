package com.devfloor.hongit.api.articlehashtag.application

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.articlehashtag.domain.ArticleHashtag
import com.devfloor.hongit.core.articlehashtag.domain.ArticleHashtagRepository
import com.devfloor.hongit.core.hashtag.domain.Hashtag
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
