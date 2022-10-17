package com.dreamsoftware.service.impl

import com.dreamsoftware.model.OtpSenderConfig
import com.dreamsoftware.service.OTPSender
import org.apache.commons.text.StringSubstitutor

abstract class SupportOTPSender<T: OtpSenderConfig> : OTPSender<T> {

    protected fun createMessage(
        otpSetting: OtpSenderConfig,
        otp: String,
        destination: String,
        properties: Map<String, String>
    ): String = StringSubstitutor(buildMap<String, String> {
        putAll(properties)
        put("destination", destination)
        put("otp", otp)
    })
        .replace(otpSetting.messageTemplate)

}