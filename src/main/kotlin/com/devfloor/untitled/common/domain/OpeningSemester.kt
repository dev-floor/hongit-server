package com.devfloor.untitled.common.domain

import java.time.Year
import javax.persistence.Embeddable

@Embeddable
class OpeningSemester(
    year: Year,
    semester: Semester,
)
