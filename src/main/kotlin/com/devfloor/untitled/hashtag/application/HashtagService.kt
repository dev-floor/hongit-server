package com.devfloor.untitled.hashtag.application

import com.devfloor.untitled.hashtag.domain.Hashtag
import com.devfloor.untitled.hashtag.domain.HashtagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HashtagService(
    private val repository: HashtagRepository
) {
    @Transactional(readOnly = true)
    fun showByName(hashtag: String): Hashtag {
        return repository.findByName(hashtag)
    }

    @Transactional(readOnly = true)
    fun showAllByNames(hashtags: List<String>): List<Hashtag> {
        return repository.findAllByNameIn(hashtags)
    }

    @Transactional(readOnly = true)
    fun existsByName(hashtag: String): Boolean {
        return repository.existsByName(hashtag)
    }

    fun create(hashtag: String): Hashtag {
        return repository.save(Hashtag(hashtag))
    }

    @Transactional
    fun createAll(hashtags: List<String>): List<Hashtag> {
        return hashtags.map { repository.save(Hashtag(it)) }
    }
}
