package com.devfloor.untitled.option.domain

import com.devfloor.untitled.article.domain.Article
import org.springframework.data.jpa.repository.JpaRepository

interface OptionRepository : JpaRepository<Option, Long> {
    fun findAllByArticle(article: Article): List<Option>?
}