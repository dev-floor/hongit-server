package com.devfloor.untitled.comment.presentation

import com.devfloor.untitled.comment.application.CommentFacade
import com.devfloor.untitled.comment.application.CommentService
import com.devfloor.untitled.comment.application.request.CommentRequest
import com.devfloor.untitled.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentController(
    private val commentFacade: CommentFacade,
    private val commentService: CommentService,
) {
    @GetMapping(value = ["/articles/{articleId}/comments"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByArticleId(@PathVariable articleId: Long) =
        commentFacade.showAllByArticleId(articleId)

    @PostMapping(value = ["/articles/{articleId}/comments"])
    @ResponseStatus(value = HttpStatus.CREATED)
    fun create(
        @PathVariable articleId: Long,
        @RequestBody request: CommentRequest,
        author: User,
    ) {
        commentService.create(articleId, author, request)
    }
}
