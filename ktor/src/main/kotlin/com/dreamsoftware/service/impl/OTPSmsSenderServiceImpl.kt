package com.dreamsoftware.service.impl

import com.dreamsoftware.model.SmsSenderConfig

class OTPSmsSenderServiceImpl: SupportOTPSender<SmsSenderConfig>() {

    override suspend fun sendOTP(
        otpSetting: SmsSenderConfig,
        otp: String,
        destination: String,
        properties: Map<String, String>
    ) {
        println("OTPSmsSenderServiceImpl - sendOTP CALLED!")
    }
}