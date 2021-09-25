package com.devfloor.hongit.api.common.utils

import org.springframework.util.StringUtils

object ApiUtils {
    private const val MASKING_START_INDEX = 2
    private const val MASKING_CHARACTER = "*"

    /**
     * 따로 규정은 없지만 최초 2자리 이후 모든 자리를 *로 마스킹 처리
     */
    fun mask(text: String): String =
        if (StringUtils.hasText(text))
            text.replaceRange(
                replacement = MASKING_CHARACTER.repeat(text.length - MASKING_START_INDEX),
                startIndex = MASKING_START_INDEX,
                endIndex = text.length,
            )
        else text
}
