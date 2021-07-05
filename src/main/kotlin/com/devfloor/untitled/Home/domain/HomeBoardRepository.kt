package com.devfloor.untitled.Home.domain

import org.springframework.data.jpa.repository.JpaRepository

interface HomeBoardRepository : JpaRepository<HomeBoard, Long> {
}
