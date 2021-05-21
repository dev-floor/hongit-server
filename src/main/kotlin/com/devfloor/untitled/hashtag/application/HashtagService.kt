package com.devfloor.untitled.hashtag.application

import com.devfloor.untitled.hashtag.domain.Hashtag
import com.devfloor.untitled.hashtag.domain.HashtagRepository
import org.springframework.stereotype.Service

@Service
class HashtagService(
    private val repository: HashtagRepository,
) {
    fun createByName(hashtagName: String): Hashtag {
        return repository.findByName(hashtagName) ?: repository.save(Hashtag(hashtagName))
    }
}
