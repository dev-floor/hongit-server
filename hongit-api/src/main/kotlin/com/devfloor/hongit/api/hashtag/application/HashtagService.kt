package com.devfloor.hongit.api.hashtag.application

import com.devfloor.hongit.core.hashtag.domain.Hashtag
import com.devfloor.hongit.core.hashtag.domain.HashtagRepository
import com.devfloor.hongit.core.hashtag.domain.findByNameOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HashtagService(
    private val hashtagRepository: HashtagRepository,
) {
    @Transactional
    fun createByName(name: String): Hashtag = hashtagRepository.findByNameOrNull(name)
        ?: hashtagRepository.save(Hashtag(name))

    @Transactional
    fun createAllByNames(names: List<String>): List<Hashtag> = names.map(this::createByName)
}
