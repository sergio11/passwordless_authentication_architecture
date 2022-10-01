package com.dreamsoftware.service.impl

import com.dreamsoftware.model.OtpSenderConfig

class OTPPushSenderServiceImpl: SupportOTPSender() {
    override fun sendOTP(
        otpSetting: OtpSenderConfig,
        otp: String,
        destination: String,
        properties: Map<String, String>
    ) {
        println("OTPMailSenderServiceImpl - sendOTP CALLED!")
    }
}