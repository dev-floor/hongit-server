package com.devfloor.untitled.board.application.response

import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.common.application.response.BaseEnumResponse
import com.devfloor.untitled.common.domain.Grade

data class BoardFeedResponse(
    val id: Long,
    val title: String,
    val type: BaseEnumResponse,
    val grade: BaseEnumResponse?,
) {
    constructor(
        board: Board,
        grade: Grade?,
    ) : this(
        id = board.id,
        title = board.title,
        type = BaseEnumResponse(board.type),
        grade = grade?.let { BaseEnumResponse(it) },
    )
}
