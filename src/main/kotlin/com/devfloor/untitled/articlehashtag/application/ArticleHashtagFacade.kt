package com.devfloor.untitled.articlehashtag.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.hashtag.application.HashtagService
import org.springframework.stereotype.Service

@Service
class ArticleHashtagFacade(
    private val articleHashtagService: ArticleHashtagService,
    private val hashtagService: HashtagService,
) {
    fun modifyByArticle(article: Article, hashtagsRequest: List<String>) {
        val hashtags = articleHashtagService.showAllByArticle(article)
        articleHashtagService.destroyAllByArticle(article)
        hashtagService.destroyAll(
            hashtags.filter { !articleHashtagService.existsById(it.id) }
                .map { it.hashtag }
        )
    }
}
