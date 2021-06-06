package com.devfloor.untitled.professor.domain

import org.springframework.data.jpa.repository.JpaRepository

interface ProfessorRepository : JpaRepository<Professor, Long>
