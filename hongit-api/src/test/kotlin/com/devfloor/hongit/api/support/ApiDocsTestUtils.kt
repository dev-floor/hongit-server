package com.devfloor.hongit.api.support

import com.devfloor.hongit.api.security.web.DefaultLoginUserArgumentResolver
import com.devfloor.hongit.core.user.domain.UserRepository
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import org.springframework.web.filter.CharacterEncodingFilter

object ApiDocsTestUtils {
    val TEST_AUTHORIZATION_HEADER = "Bearer"
    private val MESSAGE_CONVERTER = MappingJackson2HttpMessageConverter(Jackson2ObjectMapperBuilder().build())

    fun getRestDocsMockMvc(
        restDocumentation: RestDocumentationContextProvider,
        controller: Any,
    ): MockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(MESSAGE_CONVERTER)
            .addFilter<StandaloneMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .apply<StandaloneMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .alwaysDo<StandaloneMockMvcBuilder>(MockMvcResultHandlers.print())
            .build()

    fun getRestDocsMockMvcWithLoginUser(
        restDocumentation: RestDocumentationContextProvider,
        controller: Any,
        userRepository: UserRepository,
    ): MockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(MESSAGE_CONVERTER)
            .setCustomArgumentResolvers(DefaultLoginUserArgumentResolver(userRepository))
            .addFilter<StandaloneMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .apply<StandaloneMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .alwaysDo<StandaloneMockMvcBuilder>(MockMvcResultHandlers.print())
            .build()

    fun convertAsJson(value: Any): String = MESSAGE_CONVERTER.objectMapper.writeValueAsString(value)
}
