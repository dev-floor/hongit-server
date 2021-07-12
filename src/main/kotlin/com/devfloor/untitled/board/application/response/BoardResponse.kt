package com.devfloor.untitled.board.application.response

import com.devfloor.untitled.board.domain.Board
import com.devfloor.untitled.common.application.response.BaseEnumResponse
import com.devfloor.untitled.course.domain.Course
import com.devfloor.untitled.option.application.response.OptionResponse
import com.devfloor.untitled.option.domain.Option
import com.devfloor.untitled.professor.application.response.ProfessorResponse

data class BoardResponse(
    val id: Long,
    val title: String,
    val professor: ProfessorResponse?,
    val subject: String?,
    val grade: BaseEnumResponse?,
    val type: BaseEnumResponse,
    val options: List<OptionResponse>,
) {
    constructor(
        board: Board,
        options: List<Option>,
        course: Course? = null,
    ) : this(
        id = board.id,
        title = board.title,
        professor = course?.let { ProfessorResponse(it.professor) },
        subject = course?.subject?.name,
        grade = course?.let { BaseEnumResponse(it.grade) },
        type = BaseEnumResponse(board.type),
        options = options.map { OptionResponse(it) }
    )

    constructor(
        board: Board,
        course: Course?,
    ) : this(
        id = board.id,
        title = board.title,
        professor = course?.let { ProfessorResponse(it.professor) },
        subject = course?.subject?.name,
        grade = course?.let { BaseEnumResponse(it.grade) },
        type = BaseEnumResponse(board.type),
        options = emptyList()
    )
}
