package com.dreamsoftware.service

import com.dreamsoftware.model.OtpSenderConfig

interface OTPSender {
    fun sendOTP(otpSetting: OtpSenderConfig, otp: String, destination: String, properties: Map<String, String>)
}