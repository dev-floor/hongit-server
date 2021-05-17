package com.devfloor.untitled.hashtag.application

import com.devfloor.untitled.hashtag.domain.Hashtag
import com.devfloor.untitled.hashtag.domain.HashtagRepository
import org.springframework.stereotype.Service

@Service
class HashtagService(
    private val repository: HashtagRepository
) {
//    fun showByName(hashtagName: String): Hashtag? {
//        return repository.findByName(hashtagName)
//    }

//    fun showAllByNames(hashtags: List<String>): List<Hashtag> {
//        return repository.findAllByNameIn(hashtags)
//    }

//    fun create(hashtag: Hashtag): Hashtag {
//        return repository.save(hashtag)
//    }

//    fun createAll(hashtags: List<Hashtag>): List<Hashtag> {
//        return repository.saveAll(hashtags)
//    }

//    fun createAllByNames(hashtagNames: List<String>): List<Hashtag> {
//        return hashtagNames.map {
//            repository.findByName(it) ?: repository.save(Hashtag(it))
//        }
//    }

    fun createByName(hashtagName: String): Hashtag {
        return repository.findByName(hashtagName) ?: repository.save(Hashtag(hashtagName))
    }
}
