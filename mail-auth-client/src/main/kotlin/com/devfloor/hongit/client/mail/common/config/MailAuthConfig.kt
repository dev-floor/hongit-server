package com.devfloor.hongit.client.mail.common.config

import com.devfloor.hongit.client.mail.domain.impl.DefaultMailSender
import com.devfloor.hongit.client.mail.domain.impl.ProdMailSender
import com.devfloor.hongit.client.mail.domain.spec.MailSender
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templatemode.TemplateMode
import java.util.Properties

@Configuration
@EnableConfigurationProperties(value = [MailSenderProperties::class, MailAuthProperties::class])
class MailAuthConfig(
    private val authProperties: MailAuthProperties,
    private val senderProperties: MailSenderProperties,
) {
    @Bean
    @Profile(value = ["!prod"])
    fun defaultMailSender(javaMailSender: JavaMailSender): MailSender = DefaultMailSender(javaMailSender)

    @Bean
    @Profile(value = ["prod"])
    fun prodMailSender(javaMailSender: JavaMailSender, templateEngine: SpringTemplateEngine): MailSender =
        ProdMailSender(javaMailSender, authProperties, templateEngine)

    @Bean
    @Profile(value = ["!prod"])
    fun defaultJavaMailSender(): JavaMailSender = JavaMailSenderImpl()

    @Bean
    @Profile(value = ["prod"])
    fun prodJavaMailSender(): JavaMailSender = JavaMailSenderImpl().apply {
        host = senderProperties.host
        port = senderProperties.port
        username = senderProperties.username
        password = senderProperties.password
        configurationJavaMailProperties(javaMailProperties)
    }

    private fun configurationJavaMailProperties(javaMailProperties: Properties) {
        javaMailProperties["mail.transport.protocol"] = senderProperties.protocol
        javaMailProperties["mail.smtp.auth"] = senderProperties.auth
        javaMailProperties["mail.smtp.starttls.enable"] = senderProperties.starttlsEnable
        javaMailProperties["mail.smtp.starttls.required"] = senderProperties.starttlsRequired
        javaMailProperties["mail.debug"] = senderProperties.debug
    }

    @Bean
    fun templateEngine(resolver: SpringResourceTemplateResolver): SpringTemplateEngine = SpringTemplateEngine().apply {
        setTemplateResolver(resolver)
    }

    @Bean
    fun thymeleafTemplateResolver(): SpringResourceTemplateResolver = SpringResourceTemplateResolver().apply {
        prefix = "classpath:templates/"
        suffix = ".html"
        templateMode = TemplateMode.HTML
    }
}
