package com.devfloor.untitled.option.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.option.domain.OptionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OptionService(
    val repository: OptionRepository
) {
    @Transactional
    fun findAllByArticle(article: Article) =
        repository.findAllByArticle(article) ?: emptyList()
}
