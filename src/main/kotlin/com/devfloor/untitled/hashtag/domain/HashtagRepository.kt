package com.devfloor.untitled.hashtag.domain

import org.springframework.data.jpa.repository.JpaRepository

interface HashtagRepository : JpaRepository<Hashtag, Long> {
}
