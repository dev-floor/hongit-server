package com.devfloor.untitled.board.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface BoardRepository : JpaRepository<Board, Long>
