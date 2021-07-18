package com.devfloor.hongit.api.commentfavorite.presentation

import com.devfloor.hongit.api.commentfavorite.application.CommentFavoriteService
import com.devfloor.hongit.api.commentfavorite.presentation.CommentFavoriteController.Companion.COMMENT_FAVORITE_API_URI
import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.security.core.LoginUser
import com.devfloor.hongit.core.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping(value = [COMMENT_FAVORITE_API_URI])
class CommentFavoriteController(
    private val commentFavoriteService: CommentFavoriteService,
) {
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun create(@RequestParam commentId: Long, @LoginUser user: User): ResponseEntity<Unit> {
        val commentFavoriteId = commentFavoriteService.create(commentId, user)
        return ResponseEntity.created(URI.create("$COMMENT_FAVORITE_API_URI/$commentFavoriteId")).build()
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun destroy(@RequestParam commentId: Long, @LoginUser user: User) =
        commentFavoriteService.destroy(commentId, user)

    companion object {
        const val COMMENT_FAVORITE_API_URI = "$BASE_API_URI/comment-favorites"
    }
}
