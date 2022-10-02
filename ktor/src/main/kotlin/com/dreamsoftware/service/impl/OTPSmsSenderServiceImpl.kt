package com.dreamsoftware.service.impl

import com.dreamsoftware.model.OtpSenderConfig

class OTPSmsSenderServiceImpl: SupportOTPSender() {

    override fun sendOTP(
        otpSetting: OtpSenderConfig,
        otp: String,
        destination: String,
        properties: Map<String, String>
    ) {
        println("OTPSmsSenderServiceImpl - sendOTP CALLED!")
    }
}