package com.devfloor.untitled.option.application.response

import com.devfloor.untitled.common.application.response.BaseEnumResponse
import com.devfloor.untitled.option.domain.Option

data class OptionResponse(
    val text: String,
    val type: BaseEnumResponse,
) {
    companion object {
        fun from(option: Option) = OptionResponse(option.text, BaseEnumResponse.from(option.type))
    }
}
