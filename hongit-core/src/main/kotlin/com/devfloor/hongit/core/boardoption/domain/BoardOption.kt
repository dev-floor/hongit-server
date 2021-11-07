package com.devfloor.hongit.core.boardoption.domain

import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.common.domain.BaseEntity
import com.devfloor.hongit.core.option.domain.Option
import com.devfloor.hongit.core.option.domain.OptionType
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
    id: Long = 0,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_option_id")
    val id: Long = id

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

    fun withId(id: Long): BoardOption = BoardOption(board, option, id)
}
