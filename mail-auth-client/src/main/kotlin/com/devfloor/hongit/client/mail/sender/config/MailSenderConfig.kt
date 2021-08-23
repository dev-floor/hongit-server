package com.devfloor.hongit.client.mail.sender.config

import com.devfloor.hongit.client.mail.sender.impl.DefaultMailSender
import com.devfloor.hongit.client.mail.sender.impl.ProdMailSender
import com.devfloor.hongit.client.mail.sender.spec.MailSender
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.Properties

@Configuration
@EnableConfigurationProperties(MailSenderProperties::class)
class MailSenderConfig(
    private val properties: MailSenderProperties,
) {
    @Bean
    @Profile(value = ["!prod"])
    fun defaultMailSender(): MailSender = DefaultMailSender()

    @Bean
    @Profile(value = ["prod"])
    fun prodMailSender(javaMailSender: JavaMailSender): MailSender = ProdMailSender(javaMailSender)

    @Bean
    @Profile(value = ["prod"])
    fun javaMailSender(): JavaMailSender = JavaMailSenderImpl().apply {
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
}
