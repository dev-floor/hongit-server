package com.devfloor.untitled.course.domain

import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository : JpaRepository<Course, Long>
