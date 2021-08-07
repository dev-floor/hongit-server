package com.devfloor.hongit.core.bookmarkboard.domain

import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.common.domain.BaseEntity
import com.devfloor.hongit.core.user.domain.User
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
    name = "bookmark_board",
    indexes = [
        Index(name = "idx_user_id", columnList = "user_id")
    ]
)
class BookmarkBoard(
    board: Board,
    user: User,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "board_id")
    val board: Board = board

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User = user
}
