package com.devfloor.untitled.commentfavorite.presentation

import com.devfloor.untitled.commentfavorite.application.CommentFavoriteService
import com.devfloor.untitled.commentfavorite.presentation.CommentFavoriteController.Companion.COMMENT_FAVORITE_API_URI
import com.devfloor.untitled.common.config.auth.LoginUser
import com.devfloor.untitled.common.utils.BASE_API_URI
import com.devfloor.untitled.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [COMMENT_FAVORITE_API_URI])
class CommentFavoriteController(
    private val commentFavoriteService: CommentFavoriteService,
) {
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun create(@RequestParam commentId: Long, @LoginUser user: User) =
        commentFavoriteService.create(commentId, user)

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun destroy(@RequestParam commentId: Long, @LoginUser user: User) =
        commentFavoriteService.destroy(commentId, user)

    companion object {
        const val COMMENT_FAVORITE_API_URI = "$BASE_API_URI/comment-favorites"
    }
}
