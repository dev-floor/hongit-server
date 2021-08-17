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
    COURSE_BOARD("수업게시판"),
    QNA_BOARD("질문게시판"),
    COMMUNITY_BOARD("커뮤니티게시판"),
    GATHERING_BOARD("구인게시판"),
    RECRUIT_BOARD("채용게시판"),
    ;

    override val id: String
        get() = this.name
}
