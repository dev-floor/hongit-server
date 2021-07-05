package com.devfloor.untitled.Home.domain

import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

/**
 * 홈 게시판 정보를 관리하는 entity
 *
 * @property id 아이디
 * @property professor 교수
 * @property subject 과목
 * @property openingSemester 개설학기
 * @property type 게시판 종류
 */
@Entity
@Table(
    name = "homeBoards"
)
class HomeBoard(
    board: Board,
    type: HomeBoardType,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_board_id")
    val id: Long = 0

    @OneToOne
    @JoinColumn(name = "board_id")
    val board: Board = board

    @Column(name = "home_board_type")
    @Enumerated(value = EnumType.STRING)
    val type: HomeBoardType = type

    fun matchType(type: HomeBoardType): Boolean = this.type == type
}
