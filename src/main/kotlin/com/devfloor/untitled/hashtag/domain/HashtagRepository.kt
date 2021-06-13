package com.devfloor.untitled.hashtag.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * 해시태그를 관리하는 repository
 */
@Transactional(readOnly = true)
interface HashtagRepository : JpaRepository<Hashtag, Long> {
    fun findByName(name: String): Optional<Hashtag>
}

fun HashtagRepository.findByNameOrNull(name: String): Hashtag? = findByName(name).orElse(null)
