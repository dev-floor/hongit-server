package com.devfloor.untitled.comment.presentation

import com.devfloor.untitled.comment.application.CommentModifyRequest
import com.devfloor.untitled.comment.application.CommentService
import com.devfloor.untitled.comment.application.request.CommentRequest
import com.devfloor.untitled.comment.application.response.CommentResponse
import com.devfloor.untitled.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentController(
    private val commentService: CommentService,
) {
    @GetMapping(value = ["/articles/{articleId}/comments"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByArticleId(@PathVariable articleId: Long): List<CommentResponse> =
        commentService.showAllByArticleId(articleId)

    @PostMapping(value = ["/articles/{articleId}/comments"])
    @ResponseStatus(value = HttpStatus.CREATED)
    fun create(
        @PathVariable articleId: Long,
        @RequestBody request: CommentRequest,
        author: User,
    ): CommentResponse {
        return commentService.create(articleId, author, request)
    }

    @PutMapping(value = ["/comments/{commentId}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun modifyByCommentId(
        @RequestParam("articleId") articleId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: CommentModifyRequest,
    ) {
        commentService.modifyByCommentId(commentId, request)
    }

    @DeleteMapping(value = ["/comments/{commentId}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun destroyByCommentId(
        @PathVariable commentId: Long,
    ) = commentService.destroyByCommentId(commentId)
}
