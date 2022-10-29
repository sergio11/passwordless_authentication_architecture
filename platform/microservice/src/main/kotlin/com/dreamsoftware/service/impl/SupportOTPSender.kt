package com.dreamsoftware.service.impl

import com.dreamsoftware.model.OtpSenderConfig
import com.dreamsoftware.service.OTPSender
import org.apache.commons.text.StringSubstitutor

abstract class SupportOTPSender<T : OtpSenderConfig> : OTPSender<T> {

    private companion object {
        const val VARIABLE_PREFIX = "#{"
        const val VARIABLE_SUFFIX = "}"
    }

    protected open fun createMessage(
        otpSetting: OtpSenderConfig,
        otp: String,
        destination: String,
        properties: Map<String, String>
    ): String = StringSubstitutor(buildMap<String, String> {
        putAll(properties)
        put("destination", destination)
        put("otp", otp)
    })
        .setVariablePrefix(VARIABLE_PREFIX)
        .setVariableSuffix(VARIABLE_SUFFIX)
        .replace(otpSetting.messageTemplate)

}