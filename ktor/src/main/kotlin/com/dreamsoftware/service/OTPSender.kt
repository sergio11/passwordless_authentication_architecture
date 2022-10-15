package com.dreamsoftware.service

import com.dreamsoftware.model.OtpSenderConfig
import com.dreamsoftware.model.exception.OTPSenderFailedException

interface OTPSender {

    @Throws(OTPSenderFailedException::class)
    suspend fun sendOTP(otpSetting: OtpSenderConfig, otp: String, destination: String, properties: Map<String, String>)
}