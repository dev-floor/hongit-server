package com.devfloor.untitled.common.domain

import javax.persistence.Embeddable

@Embeddable
class OpeningSemester(
    year: String,
    semester: Semester,
)
