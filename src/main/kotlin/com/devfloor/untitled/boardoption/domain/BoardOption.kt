package com.devfloor.untitled.boardoption.domain

import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.option.domain.Option
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

/**
 * 게시판과 옵션 정보를 관리하는 연관관계 매핑 entity
 *
 * @property id 아이디
 * @property board 게시판
 * @property option 옵션
 */
class BoardOption(
    board: Board,
    option: Option,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_option_id")
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "board_id")
    val board: Board = board

    @ManyToOne
    @JoinColumn(name = "option_id")
    val option: Option = option
}
