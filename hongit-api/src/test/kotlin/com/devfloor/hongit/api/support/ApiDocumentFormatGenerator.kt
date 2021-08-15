package com.devfloor.hongit.api.support

import com.devfloor.hongit.core.common.domain.BaseEnum
import com.devfloor.hongit.core.common.utils.LOCAL_DATE_TIME_FORMAT
import org.springframework.restdocs.snippet.Attributes

object ApiDocumentFormatGenerator {
    private const val SEPARATOR = "\n"

    fun required(): Attributes.Attribute = Attributes.key("required").value("true")

    fun optional(): Attributes.Attribute = Attributes.key("required").value("false")

    fun customFormat(format: String): Attributes.Attribute = Attributes.key("format").value(format)

    fun customFormat(formats: List<String>): Attributes.Attribute = customFormat(formats.joinToString(SEPARATOR))

    fun emptyFormat(): Attributes.Attribute = customFormat("")

    fun dateTimeFormat(): Attributes.Attribute = customFormat(LOCAL_DATE_TIME_FORMAT)

    fun enumFormat(enums: Class<out BaseEnum>): Attributes.Attribute =
        Attributes.key("format")
            .value(
                enums.enumConstants
                    .map { it.id }
                    .run { joinToString(SEPARATOR) }
            )
}
