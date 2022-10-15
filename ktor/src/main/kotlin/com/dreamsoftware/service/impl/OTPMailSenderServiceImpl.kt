package com.dreamsoftware.service.impl

import com.dreamsoftware.model.MailSenderConfig

class OTPMailSenderServiceImpl: SupportOTPSender<MailSenderConfig>() {

    override suspend fun sendOTP(
        otpSetting: MailSenderConfig,
        otp: String,
        destination: String,
        properties: Map<String, String>
    ) {
        println("OTPMailSenderServiceImpl - sendOTP CALLED!")
    }
}