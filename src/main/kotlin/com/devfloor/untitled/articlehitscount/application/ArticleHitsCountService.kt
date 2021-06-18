package com.devfloor.untitled.articlehitscount.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlehitscount.domain.ArticleHitsCount
import com.devfloor.untitled.articlehitscount.domain.ArticleHitsCountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleHitsCountService(
    private val articleHitsCountRepository: ArticleHitsCountRepository,
) {
    @Transactional
    fun increase(article: Article) {
        var articleHitsCount = articleHitsCountRepository.findByArticleOrNull(article)
        if (articleHitsCount == null) {
            articleHitsCount = ArticleHitsCount(article)
        }
        articleHitsCount.increase()
        articleHitsCountRepository.save(articleHitsCount)
    }

    @Transactional
    fun decrease(article: Article) {
        var articleHitsCount = articleHitsCountRepository.findByArticleOrNull(article)
        if (articleHitsCount != null) {
            articleHitsCount.decrease()
            articleHitsCountRepository.save(articleHitsCount)
        }
    }
}
