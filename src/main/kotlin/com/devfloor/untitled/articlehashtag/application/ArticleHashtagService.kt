package com.devfloor.untitled.articlehashtag.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtagRepository
import com.devfloor.untitled.hashtag.domain.Hashtag
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleHashtagService(
    private val repository: ArticleHashtagRepository
) {
    @Transactional(readOnly = true)
    fun showAllByArticle(article: Article): List<ArticleHashtag> {
        return repository.findAllByArticle(article)
    }

    @Transactional
    fun createAllByHashtags(article: Article, hashtagList: List<Hashtag>) {
        hashtagList.forEach() {
            repository.save(ArticleHashtag(article, it))
        }
    }

    @Transactional
    fun createByHashtag(article: Article, hashtag: Hashtag) {
        repository.save(ArticleHashtag(article, hashtag))
    }
}
