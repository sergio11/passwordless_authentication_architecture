package com.dreamsoftware.service

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.OtpSenderConfig

interface OTPGenerator {

    fun generate(senderConfig: OtpSenderConfig, dest: String): OTPGenerated
}