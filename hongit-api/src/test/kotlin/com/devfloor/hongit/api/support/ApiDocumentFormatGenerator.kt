package com.devfloor.hongit.api.support

import com.devfloor.hongit.core.common.domain.BaseEnum
import com.devfloor.hongit.core.common.utils.LOCAL_DATE_TIME_FORMAT
import org.apache.logging.log4j.util.Strings
import org.springframework.restdocs.snippet.Attributes

object ApiDocumentFormatGenerator {
    fun required(): Attributes.Attribute = Attributes.key("required").value("true")

    fun optional(): Attributes.Attribute = Attributes.key("required").value("false")

    fun customFormat(format: String): Attributes.Attribute = Attributes.key("format").value(format)

    fun customFormat(formats: List<String>): Attributes.Attribute = customFormat(formats.joinToString(Strings.LINE_SEPARATOR))

    fun emptyFormat(): Attributes.Attribute = customFormat(Strings.EMPTY)

    fun dateTimeFormat(): Attributes.Attribute = customFormat(LOCAL_DATE_TIME_FORMAT)

    inline fun <reified T: Enum<T>> enumFormat(): Attributes.Attribute =
        Attributes.key("format")
            .value(
                enumValues<T>()
                    .map { it.name }
                    .run { joinToString(Strings.LINE_SEPARATOR) }
            )
}
