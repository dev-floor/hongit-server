package com.devfloor.untitled.articlefavorite.presentation

import com.devfloor.untitled.article.presentation.ArticleController
import com.devfloor.untitled.articlefavorite.application.ArticleFavoriteService
import com.devfloor.untitled.articlefavorite.presentation.ArticleFavoriteController.Companion.FAVORITE_API_URI
import com.devfloor.untitled.common.config.auth.LoginUser
import com.devfloor.untitled.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["${ArticleController.ARTICLE_API_URI}/{articleId}$FAVORITE_API_URI"])
class ArticleFavoriteController(
    private val articleFavoriteService: ArticleFavoriteService,
) {
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun create(
        @PathVariable articleId: Long,
        @RequestBody type: String,
        @LoginUser user: User,
    ) = articleFavoriteService.create(articleId, type, user)

    @DeleteMapping(value = ["{favoriteId}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun destroy(@PathVariable(value = "favoriteId") favoriteId: Long) =
        articleFavoriteService.destroy(favoriteId)

    companion object {
        const val FAVORITE_API_URI = "/favorites"
    }
}
