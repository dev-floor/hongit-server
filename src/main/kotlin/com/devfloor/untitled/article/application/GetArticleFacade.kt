package com.devfloor.untitled.article.application

import com.devfloor.untitled.articleHashtag.application.ArticleHashtagService
import com.devfloor.untitled.favorite.application.FavoriteService

class GetArticleFacade(
    private val articleService: ArticleService,
    private val favoriteService: FavoriteService,
    private val articleHashtagService: ArticleHashtagService,
) {
    fun getArticleDetail(id: Long) {
        articleService.findById(id).let {

        }
    }
}