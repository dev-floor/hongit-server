package com.devfloor.untitled.common.domain

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
class OpeningSemester(
    year: Year,
    semester: Semester,
) {
    @Column(name = "year")
    val year: Year = year

    @Enumerated(value = EnumType.STRING)
    @Column(name = "semester")
    val semester: Semester = semester
}
