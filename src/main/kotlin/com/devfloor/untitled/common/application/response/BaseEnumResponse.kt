package com.devfloor.untitled.common.application.response

import com.devfloor.untitled.common.domain.BaseEnum

data class BaseEnumResponse(
    val id: String,
    val text: String,
) {
    companion object {
        fun from(enum: BaseEnum) = BaseEnumResponse(enum.id, enum.text)
    }
}
