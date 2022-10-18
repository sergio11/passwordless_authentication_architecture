package com.dreamsoftware.service.impl

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.OtpSenderConfig
import com.dreamsoftware.rest.dto.OTPGenerationRequestDTO
import com.dreamsoftware.service.OTPGenerator
import org.apache.commons.lang3.RandomStringUtils
import java.util.*

class OTPGeneratorImpl: OTPGenerator {
    override fun generate(senderConfig: OtpSenderConfig, otpGenerationRequestDTO: OTPGenerationRequestDTO): OTPGenerated =
        with(senderConfig) {
            with(otpGenerationRequestDTO) {
                OTPGenerated(
                    operationId = UUID.randomUUID().toString(),
                    otp = RandomStringUtils.random(otpLength, useLetters, useDigits),
                    ttlInSeconds = ttlMinutes * 60L,
                    expireAtInMillis = System.currentTimeMillis() + ttlMinutes * 60 * 1000,
                    destination = destination,
                    properties = properties,
                    senderType = type,
                    attempts = 1
                )
            }
        }
}