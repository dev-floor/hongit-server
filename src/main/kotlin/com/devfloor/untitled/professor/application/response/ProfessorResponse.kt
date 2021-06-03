package com.devfloor.untitled.professor.application.response

import com.devfloor.untitled.professor.domain.Professor

data class ProfessorResponse(
    val name: String,
    val email: String?,
) {
    constructor(professor: Professor) : this(professor.name, professor.email)
}
