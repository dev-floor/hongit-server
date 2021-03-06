package com.devfloor.hongit.core.board.domain

import com.devfloor.hongit.core.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * 게시판 정보를 관리하는 entity
 *
 * @property id 아이디
 * @property title 제목
 * @property type 게시판 종류
 */
@Entity
@Table(name = "boards")
class Board(
    title: String,
    type: BoardType,
    id: Long = 0,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    val id: Long = id

    @Column(name = "title")
    val title: String = title

    @Enumerated(value = EnumType.STRING)
    @Column(name = "board_type")
    val type: BoardType = type

    fun withId(id: Long): Board = Board(title, type, id)
}
