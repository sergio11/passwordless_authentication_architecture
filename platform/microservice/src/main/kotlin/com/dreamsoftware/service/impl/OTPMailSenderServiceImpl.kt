package com.dreamsoftware.service.impl

import com.dreamsoftware.model.MailSenderConfig
import com.dreamsoftware.model.exception.OTPSenderFailedException
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email

class OTPMailSenderServiceImpl: SupportOTPSender<MailSenderConfig>() {

    private companion object {
        const val MAIL_SEND_ENDPOINT = "mail/send"
        const val CONTENT_TYPE = "text/html"
        const val CONTENT_VALUE = "<h1>OTP Mail</h1>"
        const val MAIL_SENT_SUCCESSFULLY_STATUS_CODE = 202
    }

    override suspend fun sendOTP(
        otpSetting: MailSenderConfig,
        otp: String,
        destination: String,
        properties: Map<String, String>
    ) {
        println("OTPMailSenderServiceImpl - sendOTP CALLED!")
        with(otpSetting) {
            val response = SendGrid(serviceKey).api(Request().apply {
                method = Method.POST
                endpoint = MAIL_SEND_ENDPOINT
                body = Mail(Email(emailFrom).apply {
                    name = emailFromName
                }, messageTitle, Email(destination), Content().apply {
                    type = CONTENT_TYPE
                    value = CONTENT_VALUE
                }).apply {
                    personalization[0].addDynamicTemplateData(messageTitlePlaceholder, messageTitle)
                    personalization[0].addDynamicTemplateData(messageContentPlaceholder, createMessage(otpSetting, otp, destination, properties))
                    templateId = messageTemplateId
                }.build()
            })
            println("OTPMailSenderServiceImpl - response.headers -> ${response.headers} CALLED!")
            println("OTPMailSenderServiceImpl - response.body -> ${response.body} CALLED!")
            println("OTPMailSenderServiceImpl - response.statusCode -> ${response.statusCode} CALLED!")
            if(response.statusCode != MAIL_SENT_SUCCESSFULLY_STATUS_CODE) {
                throw OTPSenderFailedException("An error occurred when sending a mail")
            }
        }
    }
}