package com.devfloor.hongit.api.common.application.response

import com.devfloor.hongit.core.common.domain.BaseEnum

data class BaseEnumResponse(
    val id: String,
    val text: String,
) {
    constructor(enum: BaseEnum) : this(enum.id, enum.text)
}
