package com.dreamsoftware.service

import com.dreamsoftware.model.OtpSenderConfig
import com.dreamsoftware.model.exception.OTPSenderFailedException

interface OTPSender<T: OtpSenderConfig> {

    @Throws(OTPSenderFailedException::class)
    suspend fun sendOTP(otpSetting: T, otp: String, destination: String, properties: Map<String, String>)
}