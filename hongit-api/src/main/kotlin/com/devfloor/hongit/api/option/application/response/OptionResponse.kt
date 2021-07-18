package com.devfloor.hongit.api.option.application.response

import com.devfloor.hongit.api.common.application.response.BaseEnumResponse
import com.devfloor.hongit.core.option.domain.Option

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
