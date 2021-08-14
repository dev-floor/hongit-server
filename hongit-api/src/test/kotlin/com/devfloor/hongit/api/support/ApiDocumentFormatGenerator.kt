package com.devfloor.hongit.api.support

import org.springframework.restdocs.snippet.Attributes

object ApiDocumentFormatGenerator {
    fun required(): Attributes.Attribute = Attributes.key("required").value("true")

    fun optional(): Attributes.Attribute = Attributes.key("required").value("false")
}
