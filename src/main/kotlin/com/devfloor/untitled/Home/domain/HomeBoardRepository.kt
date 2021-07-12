package com.devfloor.untitled.Home.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface HomeBoardRepository : JpaRepository<HomeBoard, Long> {
}
