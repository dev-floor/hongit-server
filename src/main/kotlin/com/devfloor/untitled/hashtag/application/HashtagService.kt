package com.devfloor.untitled.hashtag.application

import com.devfloor.untitled.hashtag.domain.Hashtag
import com.devfloor.untitled.hashtag.domain.HashtagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HashtagService(
    private val repository: HashtagRepository
) {
    fun showByName(hashtagName: String): Hashtag {
        return repository.findByName(hashtagName)
    }

    fun showAllByNames(hashtags: List<String>): List<Hashtag> {
        return repository.findAllByNameIn(hashtags)
    }

    fun existsByName(hashtag: String): Boolean {
        return repository.existsByName(hashtag)
    }

    fun create(hashtag: Hashtag): Hashtag {
        return repository.save(hashtag)
    }

    @Transactional
    fun createAll(hashtags: List<Hashtag>): List<Hashtag> {
        return repository.saveAll(hashtags)
    }
}
