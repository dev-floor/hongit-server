package com.devfloor.untitled.articlehashtag.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.hashtag.domain.Hashtag
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtag
import com.devfloor.untitled.articlehashtag.domain.ArticleHashtagRepository
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
    fun create(article: Article, hashtagList: List<Hashtag>): List<ArticleHashtag> {
        val articleHashtagList = mutableListOf<ArticleHashtag>()
        for(index in hashtagList.indices){
            articleHashtagList.add(repository.save(ArticleHashtag(article, hashtagList[index])))
        }
        return articleHashtagList
    }


}
