package com.devfloor.untitled.article.application

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ArticleResponse(
    val options: List<String>,
    
    val title: String? = null,
    
    val anonymous: Boolean,
    
    val content: String,
    
    val author: Any,
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val createdDate: LocalDateTime,
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val modifiedDate: LocalDateTime,
    
    val hashtags: List<String>,
    
    val favorites: Long,
    
    val wonders: Long,
    
    val clips: Long
)
