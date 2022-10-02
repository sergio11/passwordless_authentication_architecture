package com.dreamsoftware.service.impl

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.OtpSenderConfig
import com.dreamsoftware.service.OTPGenerator
import org.apache.commons.lang3.RandomStringUtils
import java.util.*

class OTPGeneratorImpl: OTPGenerator {
    override fun generate(senderConfig: OtpSenderConfig, dest: String): OTPGenerated =
        with(senderConfig) {
            OTPGenerated(
                operationId = UUID.randomUUID().toString(),
                otp = RandomStringUtils.random(otpLength, useLetters, useDigits),
                expireTime = System.currentTimeMillis() + ttlMinutes * 60 * 1000,
                destination = dest
            )
        }
}