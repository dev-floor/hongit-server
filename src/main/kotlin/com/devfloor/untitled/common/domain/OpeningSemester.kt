package com.devfloor.untitled.common.domain

import java.time.Year
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class OpeningSemester(
    year: Year,
    semester: Semester,
) {
    @Column(name = "year")
    val year: Year = year

    @Column(name = "semester")
    val semester: Semester = semester
}
