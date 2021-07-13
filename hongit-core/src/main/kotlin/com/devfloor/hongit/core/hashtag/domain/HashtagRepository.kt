package com.devfloor.hongit.core.hashtag.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Transactional(readOnly = true)
interface HashtagRepository : JpaRepository<Hashtag, Long> {
    fun findByName(name: String): Optional<Hashtag>
}

fun HashtagRepository.findByNameOrNull(name: String): Hashtag? = findByName(name).orElse(null)
