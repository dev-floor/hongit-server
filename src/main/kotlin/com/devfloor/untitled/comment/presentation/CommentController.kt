package com.devfloor.untitled.comment.presentation

import com.devfloor.untitled.comment.application.CommentModifyRequest
import com.devfloor.untitled.comment.application.CommentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class CommentController(
    private val commentService: CommentService,
) {
    @PutMapping(value = ["/articles/{articleId}/comments/commentId"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun modifyByCommentId(
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: CommentModifyRequest
    ) = commentService.modifyByArticleId(articleId, commentId, request)
}
