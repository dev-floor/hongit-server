package com.devfloor.untitled.option.application.response

import com.devfloor.untitled.common.application.response.BaseEnumResponse
import com.devfloor.untitled.option.domain.Option

data class OptionResponse(
    val id: Long,
    val text: String,
    val type: BaseEnumResponse,
) {
    constructor(option: Option) : this(
        id = option.id,
        text = option.text,
        type = BaseEnumResponse(option.type)
    )
}
