package com.devfloor.hongit.api.professor.application.response

import com.devfloor.hongit.core.professor.domain.Professor

data class ProfessorResponse(
    val name: String,
    val email: String?,
) {
    constructor(professor: Professor) : this(professor.name, professor.email)
}
