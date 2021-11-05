package com.devfloor.hongit.core.bookmarkboard.domain

import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.common.domain.BaseEntity
import com.devfloor.hongit.core.user.domain.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(
    name = "board_bookmarks",
    indexes = [
        Index(name = "idx_user_id", columnList = "user_id")
    ]
)
class BoardBookmark(
    board: Board,
    user: User,
    id: Long = 0,
) : BaseEntity() {
    @Id
    @Column(name = "board_bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id

    @ManyToOne
    @JoinColumn(name = "board_id")
    val board: Board = board

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User = user

    fun withId(id: Long): BoardBookmark = BoardBookmark(board, user, id)
}
