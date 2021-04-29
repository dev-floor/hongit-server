package com.devfloor.untitled.hashtag.application

import com.devfloor.untitled.hashtag.domain.Hashtag
import com.devfloor.untitled.hashtag.domain.HashtagRepository
import com.devfloor.untitled.option.domain.Option
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HashtagService (
    private val repository: HashtagRepository
){
    @Transactional(readOnly = true)
    fun show(hashtags: List <String>): List<Hashtag> {
        return repository.findByNameIn(hashtags)
    }

    @Transactional
    fun create(hashtags: List <String>): List<Hashtag> {
        var hashtagList = mutableListOf<Hashtag>()
        for(index in hashtags.indices){
            hashtagList.add(repository.save(Hashtag(hashtags[index])))
        }
        return hashtagList
    }
}
