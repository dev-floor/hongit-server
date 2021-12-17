package com.devfloor.hongit.api.hashtag.application

import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.core.hashtag.domain.Hashtag
import com.devfloor.hongit.core.hashtag.domain.HashtagRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
internal class HashtagServiceTest {
    @InjectMocks
    private lateinit var hashtagService: HashtagService

    @Mock
    private lateinit var hashtagRepository: HashtagRepository

    private lateinit var hashtag: Hashtag

    @BeforeEach
    internal fun setUp() {
        hashtagService = HashtagService(hashtagRepository)
        hashtag = Hashtag(name = "test_hashtag", id = 1)
    }

    @Test
    internal fun `createAllByNames - Hashtag 객체가 이미 존재하는 경우 조회해 반환한다`() {
        `when`(hashtagRepository.findByName(any())).thenReturn(Optional.of(hashtag))

        val hashtags = hashtagService.createAllByNames(listOf("name", "name2"))

        assertThat(hashtags).isEqualTo(listOf(hashtag, hashtag))
    }

    @Test
    internal fun `createAllByNames - Hashtag 객체가 존재하지 않는 경우 생성해 반환한다`() {
        `when`(hashtagRepository.findByName(any())).thenReturn(Optional.empty())
        `when`(hashtagRepository.save(any())).thenReturn(hashtag)

        val hashtags = hashtagService.createAllByNames(listOf("name", "name2"))

        assertThat(hashtags).isEqualTo(listOf(hashtag, hashtag))
    }
}
