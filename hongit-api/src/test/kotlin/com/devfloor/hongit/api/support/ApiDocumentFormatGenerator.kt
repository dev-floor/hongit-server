package com.devfloor.hongit.api.support

import com.devfloor.hongit.core.common.utils.LOCAL_DATE_TIME_FORMAT
import org.apache.logging.log4j.util.Strings.LINE_SEPARATOR
import org.springframework.restdocs.snippet.AbstractDescriptor
import kotlin.reflect.KClass

object ApiDocumentFormatGenerator {
    inline fun <reified T : AbstractDescriptor<T>> T.format(format: String): T =
        this.apply { attributes["format"] = format }

    inline fun <reified T : AbstractDescriptor<T>> T.format(formats: List<String>): T =
        this.apply { attributes["format"] = formats.joinToString(",$LINE_SEPARATOR") }

    inline fun <reified T : AbstractDescriptor<T>> T.dateTimeFormat(): T =
        this.apply { attributes["format"] = LOCAL_DATE_TIME_FORMAT }

    // TODO: 2021/08/22 login 코드 구현 후 token 타입 전달받아서 저장하도록 수정
    inline fun <reified T : AbstractDescriptor<T>> T.authorizationFormat(): T =
        this.apply { attributes["format"] = "Bearer token" }

    inline fun <reified T : AbstractDescriptor<T>, reified E : Enum<E>> T.enumFormat(enum: KClass<out Enum<E>>): T =
        this.apply {
            attributes["format"] = enumValues<E>()
                .map { it.name }
                .run { joinToString(",$LINE_SEPARATOR") }
        }
}
