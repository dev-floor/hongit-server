package com.devfloor.untitled.comment.presentation

import com.devfloor.untitled.comment.application.CommentModifyRequest
import com.devfloor.untitled.comment.application.CommentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentController(
    private val commentService: CommentService,
) {
    @PutMapping(value = ["/comments/{commentId}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun modifyByCommentId(
        @RequestParam("articleId") articleId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: CommentModifyRequest,
    ) {
        commentService.modifyByCommentId(commentId, request)
    }
}
