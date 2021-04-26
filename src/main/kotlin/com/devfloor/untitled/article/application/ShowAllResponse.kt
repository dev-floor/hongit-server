package com.devfloor.untitled.article.application

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ShowAllResponse(
    val id: Long,

    val options: List<String>,

    val title: String? = null,

    val anonymous: Boolean,

    val authorName: String,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val createdDate: LocalDateTime,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val modifiedDate: LocalDateTime,

    val content: String,

    val favorites: Long,

    val wonders: Long,

    val clips: Long,
)
