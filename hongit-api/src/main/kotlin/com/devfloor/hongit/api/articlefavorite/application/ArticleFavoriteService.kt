package com.devfloor.hongit.api.articlefavorite.application

import com.devfloor.hongit.api.articlefavorite.application.request.ArticleFavoriteCreateRequest
import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.article.domain.ArticleRepository
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavorite
import com.devfloor.hongit.core.articlefavorite.domain.ArticleFavoriteRepository
import com.devfloor.hongit.core.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ArticleFavoriteService(
    private val articleFavoriteRepository: ArticleFavoriteRepository,
    private val articleRepository: ArticleRepository,
) {
    fun create(request: ArticleFavoriteCreateRequest, user: User): Long {
        val article = articleRepository.findByIdOrNull(request.articleId)
            ?: EntityNotFoundException.notExistsId(Article::class, request.articleId)

        if (articleFavoriteRepository.existsByArticleAndUser(article, user)) {
            throw IllegalArgumentException("해당 article에 대한 좋아요가 이미 존재합니다.")
        }

        return ArticleFavorite(
            article = article,
            user = user,
            type = request.type
        ).let { articleFavoriteRepository.save(it) }
            .id
    }

    fun destroy(articleId: Long, user: User) {
        val article = articleRepository.findByIdOrNull(articleId)
            ?: EntityNotFoundException.notExistsId(Article::class, articleId)

        articleFavoriteRepository.deleteByArticleAndUser(article, user)
    }
}
