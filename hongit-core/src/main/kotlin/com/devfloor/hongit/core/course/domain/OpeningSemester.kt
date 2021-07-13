package com.devfloor.hongit.core.course.domain

import java.time.Year
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 * 개설학기 정보를 관리하는 embedded 객체
 *
 * @property year 개설년도
 * @property semester 개설학기
 */
@Embeddable
data class OpeningSemester(
    @Column(name = "year")
    val year: Year,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "semester")
    val semester: Semester,
)
