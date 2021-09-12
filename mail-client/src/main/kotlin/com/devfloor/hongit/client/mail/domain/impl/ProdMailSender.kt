package com.devfloor.hongit.client.mail.domain.impl

import com.devfloor.hongit.client.mail.application.request.MailRequest
import com.devfloor.hongit.client.mail.common.utils.MAIL_SUBJECT
import com.devfloor.hongit.client.mail.common.utils.MAIL_TEMPLATE_FILE
import com.devfloor.hongit.client.mail.common.utils.SIGN_UP_URL
import com.devfloor.hongit.client.mail.domain.spec.MailSender
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import javax.mail.internet.MimeMessage

@Slf4j
class ProdMailSender(
    private val sender: JavaMailSender,
    private val templateEngine: SpringTemplateEngine,
) : MailSender {
    override fun send(message: MimeMessage): Unit = try {
        sender.send(message)
        log.info("[ProdMailSender.send] 메일 발송 완료")
    } catch (e: MailException) {
        log.error("[ProdMailSender.send] 메일 발송 실패 - errorMessage: ${e.message}")
        throw e
    }

    override fun createMimeMessage(request: MailRequest, tokenId: String): MimeMessage {
        val message = sender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setSubject(MAIL_SUBJECT)
        helper.setTo(request.receiverEmail)
        helper.setText(createContent(request, tokenId), true)

        log.info(
            "[ProdMailSender.createMimeMessage] MimeMessage 생성 완료 - " +
                "receiverEmail: ${request.receiverEmail}, tokenId: $tokenId, url: $SIGN_UP_URL"
        )
        return message
    }

    private fun createContent(request: MailRequest, tokenId: String): String =
        Context().let {
            val variables = linkedMapOf<String, Any>()
            variables["receiverEmail"] = request.receiverEmail
            variables["redirectUrl"] = "$SIGN_UP_URL?tokenId=$tokenId"
            it.setVariables(variables)

            return templateEngine.process(MAIL_TEMPLATE_FILE, it)
        }
}
