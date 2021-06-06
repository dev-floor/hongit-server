package com.devfloor.untitled.boardoption.domain

import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.common.domain.BaseEntity
import com.devfloor.untitled.option.domain.Option
import com.devfloor.untitled.option.domain.OptionType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 게시판과 옵션의 연관관계 매핑 entity
 *
 * @property id 아이디
 * @property board 게시판
 * @property option 옵션
 */
@Entity
@Table(
    name = "board_options",
    indexes = [
        Index(name = "idx_board_id", columnList = "board_id")
    ]
)
class BoardOption(
    board: Board,
    option: Option,
) : BaseEntity() {
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

    init {
        assert(option.type != OptionType.COURSE_GROUP) {
            "boardOption의 option은 optionType이 COURSE_GROUP이 아닌 옵션만 가능합니다."
        }
    }
}
