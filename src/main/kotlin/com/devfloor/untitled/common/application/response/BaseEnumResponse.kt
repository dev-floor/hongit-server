package com.devfloor.untitled.common.application.response

import com.devfloor.untitled.common.domain.BaseEnum

data class BaseEnumResponse(
    val id: String,
    val text: String,
) {
    constructor(enum: BaseEnum) : this(enum.id, enum.text)
}
