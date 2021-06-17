package com.devfloor.untitled.articlefavorite.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.article.domain.ArticleRepository
import com.devfloor.untitled.articlefavorite.domain.ArticleFavorite
import com.devfloor.untitled.articlefavorite.domain.ArticleFavoriteRepository
import com.devfloor.untitled.articlefavorite.domain.ArticleFavoriteType
import com.devfloor.untitled.common.exception.EntityNotFoundException
import com.devfloor.untitled.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ArticleFavoriteService(
    private val articleFavoriteRepository: ArticleFavoriteRepository,
    private val articleRepository: ArticleRepository,
) {
    fun create(articleId: Long, type: String, user: User) {
        val article = articleRepository.findByIdOrNull(articleId)
            ?: EntityNotFoundException.notExistsId(Article::class, articleId)

        ArticleFavorite(
            article = article,
            user = user,
            type = ArticleFavoriteType.from(type)
        ).let { articleFavoriteRepository.save(it) }
    }

    fun destroy(favoriteId: Long) = articleFavoriteRepository.deleteById(favoriteId)
}
