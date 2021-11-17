package com.devfloor.hongit.api.boardbookmark

import com.devfloor.hongit.api.boardbookmark.application.BoardBookmarkService
import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.core.bookmarkboard.domain.BoardBookmarkRepository
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.board.domain.BoardRepository
import com.devfloor.hongit.core.board.domain.BoardType
import com.devfloor.hongit.core.user.domain.Email
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyIterable
import org.mockito.Mockito.anyList
import org.mockito.Mockito.doNothing
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class BoardBookmarkServiceTest {
    @Mock
    private lateinit var boardRepository: BoardRepository

    @Mock
    private lateinit var boardBookmarkRepository: BoardBookmarkRepository

    private lateinit var boardBookmarkService: BoardBookmarkService

    private lateinit var user: User

    private lateinit var board1: Board

    private lateinit var board2: Board

    @BeforeEach
    fun setUp() {
        boardBookmarkService = BoardBookmarkService(boardRepository, boardBookmarkRepository)
        user = User(
            id = 1,
            username = "username",
            password = "password",
            nickname = "nickname",
            email = Email.from("email@g.hongik.ac.kr"),
            type = UserType.GRADUATE,
            classOf = "B411158",
            image = "https://image.com",
            github = "github",
            blog = "blog",
            description = "test user description",
        )

        board1 = Board(id = 0, title = "course", type = BoardType.COURSE_BOARD)
        board2 = Board(id = 1, title = "qna", type = BoardType.QNA_BOARD)
    }

    @Test
    internal fun `modifyAllByBoardIds - User에 해당하는 BoardBookmark를 삭제하고 입력받은 BoardBookmark를 생성한다`() {
        doNothing().`when`(boardBookmarkRepository).deleteAllByUser(any())
        `when`(boardRepository.findAllById(anyList())).thenReturn(mutableListOf(board1, board2))
        `when`(boardBookmarkRepository.saveAll(anyIterable())).thenReturn(anyList())

        val result = boardBookmarkService.modifyAllByBoardIds(listOf(0, 1), user)

        assertThat(result).isEqualTo(Unit)
    }
}
