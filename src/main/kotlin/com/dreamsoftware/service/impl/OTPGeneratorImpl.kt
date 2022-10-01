package com.dreamsoftware.service.impl

import com.dreamsoftware.model.MfaConfig
import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.exception.OTPSenderNotFoundException
import com.dreamsoftware.service.OTPGenerator
import org.apache.commons.lang3.RandomStringUtils
import java.util.*

class OTPGeneratorImpl(
    private val mfaConfig: MfaConfig
): OTPGenerator {
    override fun generate(senderId: String, dest: String): OTPGenerated =
        mfaConfig.senders.find { it.id == senderId }?.let { otpSenderConfig ->
            with(otpSenderConfig) {
                OTPGenerated(
                    operationId = UUID.randomUUID(),
                    otp = RandomStringUtils.random(otpLength, useLetters, useDigits),
                    expireTime = System.currentTimeMillis() + ttlMinutes * 60 * 1000,
                    destination = dest
                )
            }
        } ?: throw OTPSenderNotFoundException()
}