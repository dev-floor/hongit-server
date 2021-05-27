package com.devfloor.untitled.article.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.article.domain.ArticleRepository
import com.devfloor.untitled.articlefavorite.application.ArticleFavoriteService
import com.devfloor.untitled.articlehashtag.application.ArticleHashtagService
import com.devfloor.untitled.articleoption.application.ArticleOptionService
import com.devfloor.untitled.common.exception.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(
    private val repository: ArticleRepository,
    private val articleHashtagService: ArticleHashtagService,
    private val articleFavoriteService: ArticleFavoriteService,
    private val articleOptionService: ArticleOptionService,
) {
    @Transactional(readOnly = true)
    fun showByArticleId(articleId: Long): ArticleResponse {
        repository.findByIdOrNull(articleId)
            ?.let {
                val articleOptions = articleOptionService.showAllByArticle(it)
                val articleHashtags = articleHashtagService.showAllByArticle(it)
                val articleFavorites = articleFavoriteService.showAllByArticle(it)

                return ArticleResponse(
                    articleOptions = articleOptions,
                    article = it,
                    articleHashtags = articleHashtags,
                    articleFavorites = articleFavorites,
                )
            }
        throw EntityNotFoundException("사용자가 요청한 리소스가 없습니다")
    }

    fun showAll(): List<Article> = repository.findAll()

    fun create(article: Article): Article = repository.save(article)

    fun modify(id: Long, title: String?, content: String): Article =
        showById(id).apply { modify(title, content) }

    fun destroyById(id: Long) = repository.deleteById(id)
}
