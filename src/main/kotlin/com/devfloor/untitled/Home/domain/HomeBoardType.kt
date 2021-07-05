package com.devfloor.untitled.Home.domain

import com.devfloor.untitled.common.domain.BaseEnum

/**
 * 홈화면 게시판 종류를 관리하는 enum
 *
 * @property text 홈 게시판 종류
 */
enum class HomeBoardType(
    override val text: String,
) : BaseEnum {
    TOTAL_FAVORITE("전체 좋아요"),
    TOTAL_VIEW("전체 조회수"),
    BOARD("게시판"),
    ;

    override val id: String
        get() = this.name
}
