package com.dreamsoftware.service.impl

import com.dreamsoftware.model.OtpSenderConfig

class OTPMailSenderServiceImpl: SupportOTPSender() {

    override suspend fun sendOTP(
        otpSetting: OtpSenderConfig,
        otp: String,
        destination: String,
        properties: Map<String, String>
    ) {
        println("OTPMailSenderServiceImpl - sendOTP CALLED!")
    }
}