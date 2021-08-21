package com.devfloor.hongit.api.board.application.response

import com.devfloor.hongit.api.common.application.response.BaseEnumResponse
import com.devfloor.hongit.api.option.application.response.OptionResponse
import com.devfloor.hongit.api.professor.application.response.ProfessorResponse
import com.devfloor.hongit.core.board.domain.Board
import com.devfloor.hongit.core.course.domain.Course
import com.devfloor.hongit.core.option.domain.Option

data class BoardResponse(
    val id: Long,
    val title: String,
    val professor: ProfessorResponse?,
    val subject: String?,
    val grade: BaseEnumResponse?,
    val type: BaseEnumResponse,
    val options: List<OptionResponse>,
) {
    // TODO: 2021/08/21 해당 생성자를 사용하는 로직 점검해보기
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
