package com.devfloor.untitled.comment.presentation

import com.devfloor.untitled.comment.application.CommentFacade
import com.devfloor.untitled.comment.application.CommentResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentController(
    private val commentFacade: CommentFacade,
) {
    @GetMapping(value = ["/articles/{articleId}/comments"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showAllByArticleId(@PathVariable articleId: Long): List<CommentResponse> {
        return commentFacade.showAllByArticleId(articleId)
    }
}
