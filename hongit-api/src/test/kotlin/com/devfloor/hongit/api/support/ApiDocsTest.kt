package com.devfloor.hongit.api.support

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.restdocs.RestDocumentationExtension

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Tag("restDocs")
@ExtendWith(value = [MockitoExtension::class, RestDocumentationExtension::class])
annotation class ApiDocsTest
