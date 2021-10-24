package com.devfloor.hongit.client.mail.common.config

import com.devfloor.hongit.client.mail.domain.impl.DefaultMailSender
import com.devfloor.hongit.client.mail.domain.impl.ProdMailSender
import com.devfloor.hongit.client.mail.domain.spec.MailSender
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templatemode.TemplateMode
import java.util.Properties

@Configuration
@EnableConfigurationProperties(value = [MailSenderProperties::class])
class MailSenderConfig(
    private val properties: MailSenderProperties,
) {
    @Bean
    @ConditionalOnProperty(value = [MAIL_SENDER_MODE], havingValue = "stub")
    fun defaultMailSender(javaMailSender: JavaMailSender): MailSender = DefaultMailSender(javaMailSender)

    @Bean
    @ConditionalOnProperty(value = [MAIL_SENDER_MODE], havingValue = "prod")
    fun prodMailSender(javaMailSender: JavaMailSender, templateEngine: SpringTemplateEngine): MailSender =
        ProdMailSender(javaMailSender, templateEngine)

    @Bean
    @ConditionalOnProperty(value = [MAIL_SENDER_MODE], havingValue = "stub")
    fun defaultJavaMailSender(): JavaMailSender = JavaMailSenderImpl()

    @Bean
    @ConditionalOnProperty(value = [MAIL_SENDER_MODE], havingValue = "prod")
    fun prodJavaMailSender(): JavaMailSender = JavaMailSenderImpl().apply {
        host = properties.host
        port = properties.port
        username = properties.username
        password = properties.password
        configurationJavaMailProperties(javaMailProperties)
    }

    private fun configurationJavaMailProperties(javaMailProperties: Properties) {
        javaMailProperties["mail.transport.protocol"] = properties.protocol
        javaMailProperties["mail.smtp.auth"] = properties.auth
        javaMailProperties["mail.smtp.starttls.enable"] = properties.starttlsEnable
        javaMailProperties["mail.smtp.starttls.required"] = properties.starttlsRequired
        javaMailProperties["mail.debug"] = properties.debug
    }

    @Bean
    fun templateEngine(resolver: SpringResourceTemplateResolver) =
        SpringTemplateEngine().apply {
            setTemplateResolver(resolver)
        }

    @Bean
    @Primary
    fun thymeleafTemplateResolver(): SpringResourceTemplateResolver = SpringResourceTemplateResolver().apply {
        prefix = "classpath:templates/"
        suffix = ".html"
        templateMode = TemplateMode.HTML
    }

    companion object {
        const val MAIL_SENDER_MODE = "mail.sender.mode"
    }
}
