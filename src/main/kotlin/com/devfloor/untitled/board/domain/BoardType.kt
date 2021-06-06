package com.devfloor.untitled.board.domain

import com.devfloor.untitled.common.domain.BaseEnum

/**
 * 게시판 종류를 관리하는 enum
 *
 * @property text 게시판 종류
 */
enum class BoardType(
    override val text: String,
) : BaseEnum {
    COURSE_BOARD("수업게시판")
    ;

    override val id: String
        get() = this.name
}
