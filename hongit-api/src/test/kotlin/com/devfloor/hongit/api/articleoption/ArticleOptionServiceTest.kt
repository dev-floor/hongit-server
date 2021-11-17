package com.devfloor.hongit.api.articleoption

import com.devfloor.hongit.api.articleoption.application.ArticleOptionService
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.ARTICLE_1
import com.devfloor.hongit.core.articleoption.domain.ArticleOptionRepository
import com.devfloor.hongit.core.option.domain.Option
import com.devfloor.hongit.core.option.domain.OptionType
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
internal class ArticleOptionServiceTest {
    @Mock
    private lateinit var articleOptionRepository: ArticleOptionRepository

    private lateinit var articleOptionService: ArticleOptionService

    private lateinit var option1: Option

    private lateinit var option2: Option

    @BeforeEach
    fun setUp() {
        articleOptionService = ArticleOptionService(articleOptionRepository)

        option1 = Option(
            id = 1,
            text = "option 1",
            type = OptionType.ARTICLE_KIND
        )

        option2 = Option(
            id = 2,
            text = "option 2",
            type = OptionType.COURSE_GROUP,
        )
    }

    @Test
    internal fun `modifyByArticle - Article에 해당하는 Option을 삭제하고 요청받은 Option을 생성한다`() {
        doNothing().`when`(articleOptionRepository).deleteAllByArticle(any())
        `when`(articleOptionRepository.saveAll(anyList())).thenReturn(anyList())

        val result = articleOptionService.modifyByArticle(ARTICLE_1, listOf(option1, option2))

        assertThat(result).isEqualTo(Unit)
    }
}
