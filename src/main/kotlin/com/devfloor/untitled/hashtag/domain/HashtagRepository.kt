package com.devfloor.untitled.hashtag.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface HashtagRepository : JpaRepository<Hashtag, Long> {
    @Transactional(readOnly = true)
    fun findByName(hashtag: String): Hashtag

    @Transactional(readOnly = true)
    fun findAllByNameIn(hashtags: List<String>): List<Hashtag>

    @Transactional(readOnly = true)
    fun existsByName(hashtag: String): Boolean
}
