package com.devfloor.untitled.board.domain

/**
 * 게시판 종류를 관리하는 enum
 *
 * @property text 게시판 종류
 */
enum class BoardType(
    val text: String,
) {
    COURSE_BOARD("수업게시판")
    ;
}
