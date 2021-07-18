package com.devfloor.hongit.core.board.domain

import com.devfloor.hongit.core.common.domain.BaseEnum

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
