package com.devfloor.untitled.hashtag.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying

interface HashtagRepository : JpaRepository<Hashtag, Long> {
    @Modifying(clearAutomatically = true)
    fun deleteAll(hashtags: List<Hashtag>)
}
