package com.devfloor.hongit.api.comment.presentation

import com.devfloor.hongit.api.comment.application.CommentService
import com.devfloor.hongit.api.comment.application.request.CommentCreateRequest
import com.devfloor.hongit.api.comment.application.request.CommentModifyRequest
import com.devfloor.hongit.api.comment.application.response.CommentResponse
import com.devfloor.hongit.api.comment.application.response.CommentInProfileResponse
import com.devfloor.hongit.api.comment.presentation.CommentController.Companion.COMMENT_API_URI
import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.security.core.LoginUser
import com.devfloor.hongit.core.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping(value = [COMMENT_API_URI])
class CommentController(
    private val commentService: CommentService,
) {
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByArticleId(@RequestParam articleId: Long): List<CommentResponse> =
        commentService.showAllByArticleId(articleId)

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByUserId(@RequestParam userId: Long): List<CommentInProfileResponse> =
        commentService.showAllByUserId(userId)

    @PostMapping
    fun create(
        @RequestBody request: CommentCreateRequest,
        @LoginUser author: User,
    ): ResponseEntity<CommentResponse> = commentService.create(author, request)
        .run { ResponseEntity.created(URI.create("$COMMENT_API_URI/$id")).body(this) }

    @PutMapping(value = ["/{commentId}"])
    @ResponseStatus(value = HttpStatus.OK)
    fun modifyByCommentId(
        @PathVariable commentId: Long,
        @RequestBody request: CommentModifyRequest,
        @LoginUser loginUser: User,
    ): CommentResponse = commentService.modifyByCommentId(commentId, request)

    @DeleteMapping(value = ["/{commentId}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun destroyByCommentId(
        @PathVariable commentId: Long,
        @LoginUser loginUser: User,
    ) = commentService.destroyByCommentId(commentId)

    companion object {
        const val COMMENT_API_URI = "$BASE_API_URI/comments"
    }
}
