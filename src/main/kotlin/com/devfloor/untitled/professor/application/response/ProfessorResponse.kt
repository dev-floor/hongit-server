package com.devfloor.untitled.professor.application.response

import com.devfloor.untitled.professor.domain.Professor

data class ProfessorResponse(
    val name: String,
    val email: String?,
) {
    companion object {
        fun from(professor: Professor) = ProfessorResponse(professor.name, professor.email)
    }
}
