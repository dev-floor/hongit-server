package com.devfloor.untitled.article.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.article.domain.ArticleRepository
import com.devfloor.untitled.common.exception.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(
    private val repository: ArticleRepository
) {
    @Transactional(readOnly = true)
    fun showById(id: Long): Article = repository.findByIdOrNull(id)
        ?: throw EntityNotFoundException("Not found")
}
