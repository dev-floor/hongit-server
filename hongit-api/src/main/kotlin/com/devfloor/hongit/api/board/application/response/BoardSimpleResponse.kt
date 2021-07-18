package com.devfloor.hongit.api.board.application.response

import com.devfloor.hongit.api.common.application.response.BaseEnumResponse
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.course.domain.Grade

data class BoardSimpleResponse(
    val id: Long,
    val title: String,
    val type: BaseEnumResponse,
    val grade: BaseEnumResponse?,
) {
    constructor(
        board: Board,
        grade: Grade? = null,
    ) : this(
        id = board.id,
        title = board.title,
        type = BaseEnumResponse(board.type),
        grade = grade?.let { BaseEnumResponse(it) },
    )
}
