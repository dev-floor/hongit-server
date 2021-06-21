package com.devfloor.untitled.articlefavorite.presentation

import com.devfloor.untitled.articlefavorite.application.request.ArticleFavoriteCreateRequest
import com.devfloor.untitled.articlefavorite.application.ArticleFavoriteService
import com.devfloor.untitled.articlefavorite.presentation.ArticleFavoriteController.Companion.ARTICLE_FAVORITE_API_URI
import com.devfloor.untitled.common.config.auth.LoginUser
import com.devfloor.untitled.common.utils.BASE_API_URI
import com.devfloor.untitled.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [ARTICLE_FAVORITE_API_URI])
class ArticleFavoriteController(
    private val articleFavoriteService: ArticleFavoriteService,
) {
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun create(
        @RequestBody request: ArticleFavoriteCreateRequest,
        @LoginUser user: User,
    ) = articleFavoriteService.create(request, user)

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun destroy(
        @RequestParam articleId: Long,
        @LoginUser user: User,
    ) = articleFavoriteService.destroy(articleId, user)

    companion object {
        const val ARTICLE_FAVORITE_API_URI = "$BASE_API_URI/articleFavorites"
    }
}
