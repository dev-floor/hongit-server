package com.devfloor.hongit.api.article.domain

import com.devfloor.hongit.core.article.domain.Article

interface ArticleRepositoryCustom {
    fun findByFavoriteTopFive(): List<Article>
    fun findByViewCountTopFive(): List<Article>
}
