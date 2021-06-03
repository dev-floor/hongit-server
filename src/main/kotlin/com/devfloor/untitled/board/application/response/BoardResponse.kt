package com.devfloor.untitled.board.application.response

import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.common.application.response.BaseEnumResponse
import com.devfloor.untitled.common.domain.Grade
import com.devfloor.untitled.option.application.response.OptionResponse
import com.devfloor.untitled.option.domain.Option
import com.devfloor.untitled.professor.application.response.ProfessorResponse

data class BoardResponse(
    val id: Long,
    val title: String,
    val professor: ProfessorResponse,
    val subject: String,
    val type: BaseEnumResponse,
    val grade: BaseEnumResponse?,
    val options: List<OptionResponse>,
) {
    constructor(
        board: Board,
        grade: Grade?,
        options: List<Option>,
    ) : this(
        id = board.id,
        title = board.title,
        professor = ProfessorResponse.from(board.professor),
        subject = board.subject.name,
        type = BaseEnumResponse.from(board.type),
        grade = grade?.let { BaseEnumResponse.from(it) },
        options = options.map { OptionResponse.from(it) }
    )
}
