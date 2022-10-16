package com.dreamsoftware.service.impl

import com.dreamsoftware.model.MailSenderConfig
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email

class OTPMailSenderServiceImpl: SupportOTPSender<MailSenderConfig>() {

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
                body = Mail(Email(emailFrom).apply {
                    name = emailFromName
                }, messageTitle, Email(destination), Content().apply {
                    type = "text/html"
                }).apply {
                    personalization[0].addDynamicTemplateData(messageTitlePlaceholder, messageTitle)
                    personalization[0].addDynamicTemplateData(messageContentPlaceholder, createMessage(otpSetting, otp, destination, properties))
                    templateId = messageTemplateId
                }.build()
            })
            println("OTPMailSenderServiceImpl - response.body -> ${response.body} CALLED!")
            println("OTPMailSenderServiceImpl - response.statusCode -> ${response.statusCode} CALLED!")
        }
    }
}