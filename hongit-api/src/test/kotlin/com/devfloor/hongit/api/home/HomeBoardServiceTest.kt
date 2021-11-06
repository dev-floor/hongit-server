package com.devfloor.hongit.api.home

import com.devfloor.hongit.api.article.application.ArticleService
import com.devfloor.hongit.api.home.application.HomeBoardService
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.COMMUNITY_ARTICLE_HOME_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.COMMUNITY_ARTICLE_HOME_RESPONSE_2
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.GATHERING_ARTICLE_HOME_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.GATHERING_ARTICLE_HOME_RESPONSE_2
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.MOST_FAVORITED_ARTICLE_HOME_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.MOST_FAVORITED_ARTICLE_HOME_RESPONSE_2
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.MOST_VIEWED_ARTICLE_HOME_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.MOST_VIEWED_ARTICLE_HOME_RESPONSE_2
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.QNA_ARTICLE_HOME_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.QNA_ARTICLE_HOME_RESPONSE_2
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.RECRUIT_ARTICLE_HOME_RESPONSE_1
import com.devfloor.hongit.api.support.TestFixtures.ArticleFixture.RECRUIT_ARTICLE_HOME_RESPONSE_2
import com.devfloor.hongit.api.support.TestFixtures.BoardFixture.COMMUNITY_BOARD_1
import com.devfloor.hongit.api.support.TestFixtures.BoardFixture.GATHERING_BOARD_1
import com.devfloor.hongit.api.support.TestFixtures.BoardFixture.QNA_BOARD_1
import com.devfloor.hongit.api.support.TestFixtures.BoardFixture.RECRUIT_BOARD_1
import com.devfloor.hongit.core.board.domain.BoardRepository
import com.devfloor.hongit.core.board.domain.BoardType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class HomeBoardServiceTest {
    @Mock
    private lateinit var boardRepository: BoardRepository

    @Mock
    private lateinit var articleService: ArticleService

    private lateinit var homeBoardService: HomeBoardService

    @BeforeEach
    internal fun setUp() {
        homeBoardService = HomeBoardService(boardRepository, articleService)
    }

    @Test
    internal fun `showAll - 모든 HomeBoardResponses 객체 조회에 성공한다`() {
        val boards = listOf(QNA_BOARD_1, COMMUNITY_BOARD_1, GATHERING_BOARD_1, RECRUIT_BOARD_1)
        `when`(boardRepository.findAllByTypeNot(BoardType.COURSE_BOARD)).thenReturn(boards)
        `when`(articleService.showTopFiveByBoard(QNA_BOARD_1))
            .thenReturn(listOf(QNA_ARTICLE_HOME_RESPONSE_1, QNA_ARTICLE_HOME_RESPONSE_2))
        `when`(articleService.showTopFiveByBoard(COMMUNITY_BOARD_1))
            .thenReturn(listOf(COMMUNITY_ARTICLE_HOME_RESPONSE_1, COMMUNITY_ARTICLE_HOME_RESPONSE_2))
        `when`(articleService.showTopFiveByBoard(GATHERING_BOARD_1))
            .thenReturn(listOf(GATHERING_ARTICLE_HOME_RESPONSE_1, GATHERING_ARTICLE_HOME_RESPONSE_2))
        `when`(articleService.showTopFiveByBoard(RECRUIT_BOARD_1))
            .thenReturn(listOf(RECRUIT_ARTICLE_HOME_RESPONSE_1, RECRUIT_ARTICLE_HOME_RESPONSE_2))
        `when`(articleService.showTopFiveByFavorite())
            .thenReturn(listOf(MOST_FAVORITED_ARTICLE_HOME_RESPONSE_1, MOST_FAVORITED_ARTICLE_HOME_RESPONSE_2))
        `when`(articleService.showTopFiveByViewCount())
            .thenReturn(listOf(MOST_VIEWED_ARTICLE_HOME_RESPONSE_1, MOST_VIEWED_ARTICLE_HOME_RESPONSE_2))

        val result = homeBoardService.showAll()

        verify(boardRepository).findAllByTypeNot(BoardType.COURSE_BOARD)
        boards.forEach { verify(articleService).showTopFiveByBoard(it) }
        verify(articleService).showTopFiveByFavorite()
        verify(articleService).showTopFiveByViewCount()
        assertEquals(result.count(), 6)
        result.map { assertEquals(it.articles.count(), 2) }
    }
}
