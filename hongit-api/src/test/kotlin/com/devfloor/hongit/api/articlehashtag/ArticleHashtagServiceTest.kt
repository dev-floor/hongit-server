package com.devfloor.hongit.api.articlehashtag

import com.devfloor.hongit.api.articlehashtag.application.ArticleHashtagService
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.ARTICLE_1
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.core.articlehashtag.domain.ArticleHashtagRepository
import com.devfloor.hongit.core.hashtag.domain.Hashtag
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class ArticleHashtagServiceTest {
    @Mock
    private lateinit var articleHashtagRepository: ArticleHashtagRepository

    private lateinit var articleHashtagService: ArticleHashtagService

    private lateinit var hashtag1: Hashtag

    private lateinit var hashtag2: Hashtag

    @BeforeEach
    fun setUp() {
        articleHashtagService = ArticleHashtagService(articleHashtagRepository)

        hashtag1 = Hashtag(
            id = 1,
            name = "hashtagname1"
        )

        hashtag2 = Hashtag(
            id = 2,
            name = "hashtagname2"
        )
    }

    @Test
    internal fun `modifyByArticle - Article에 해당하는 Hashtag를 삭제하고 요청받은 Hashtag를 생성한다`() {
        doNothing().`when`(articleHashtagRepository).deleteAllByArticle(any())
        `when`(articleHashtagRepository.saveAll(anyList())).thenReturn(anyList())

        val result = articleHashtagService.modifyByArticle(ARTICLE_1, listOf(hashtag1, hashtag2))

        assertThat(result).isEqualTo(Unit)
    }
}
