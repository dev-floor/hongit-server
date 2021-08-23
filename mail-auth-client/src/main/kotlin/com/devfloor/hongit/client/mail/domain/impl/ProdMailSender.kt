package com.devfloor.hongit.client.mail.domain.impl

import com.devfloor.hongit.client.mail.application.request.MailSendRequest
import com.devfloor.hongit.client.mail.common.config.MailAuthProperties
import com.devfloor.hongit.client.mail.common.utils.MAIL_SUBJECT
import com.devfloor.hongit.client.mail.common.utils.MAIL_TEMPLATE_FILE
import com.devfloor.hongit.client.mail.domain.spec.MailSender
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import javax.mail.internet.MimeMessage

@Slf4j
class ProdMailSender(
    private val sender: JavaMailSender,
    private val properties: MailAuthProperties,
    private val templateEngine: TemplateEngine,
) : MailSender {
    override fun send(message: MimeMessage): Unit = try {
        sender.send(message)
        log.info("[ProdMailSender.send] 메일 발송 완료")
    } catch (e: MailException) {
        log.error("[ProdMailSender.send] 메일 발송 실패 - errorMessage: ${e.message}")
        throw e
    }

    override fun createMimeMessage(request: MailSendRequest, tokenId: String): MimeMessage {
        val message = sender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        helper.setSubject(MAIL_SUBJECT)
        helper.setTo(request.receiverEmail)
        helper.setText(createContent(request, tokenId), true)

        log.info("[ProdMailSender.createMimeMessage] MimeMessage 생성 완료 - receiverEmail: ${request.receiverEmail}, " +
            "tokenId: $tokenId, authUrl: ${properties.authUrl}")
        return message
    }

    private fun createContent(request: MailSendRequest, tokenId: String): String =
        Context().let {
            val variables = linkedMapOf<String, Any>()
            variables["receiverEmail"] = request.receiverEmail
            variables["redirectUrl"] = "${properties.authUrl}?tokenId=$tokenId"
            it.setVariables(variables)

            return templateEngine.process(MAIL_TEMPLATE_FILE, it)
        }
}
