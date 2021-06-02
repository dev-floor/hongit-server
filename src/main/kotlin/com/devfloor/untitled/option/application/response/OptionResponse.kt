package com.devfloor.untitled.option.application.response

import com.devfloor.untitled.common.application.response.BaseEnumResponse
import com.devfloor.untitled.option.domain.Option

data class OptionResponse(
    val id: Long,
    val text: String,
    val type: BaseEnumResponse,
) {
    companion object {
        fun from(option: Option) = OptionResponse(
            id = option.id,
            text = option.text,
            type = BaseEnumResponse.from(option.type)
        )
    }
}
