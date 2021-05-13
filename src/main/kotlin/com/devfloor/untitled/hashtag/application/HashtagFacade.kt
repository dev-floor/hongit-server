package com.devfloor.untitled.article.application

import com.devfloor.untitled.hashtag.application.HashtagService
import com.devfloor.untitled.hashtag.domain.Hashtag
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HashtagFacade(
    private val hashtagService: HashtagService,
) {
    @Transactional
    fun createAll(hashtags: List<String>): List<Hashtag> {
        return hashtags.map {
            if (!hashtagService.existsByName(it))
                hashtagService.create(Hashtag(it))
            else hashtagService.showByName(it)
        }
    }
}
