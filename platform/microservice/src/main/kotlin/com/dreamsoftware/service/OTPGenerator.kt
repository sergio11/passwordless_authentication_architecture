package com.dreamsoftware.service

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.OtpSenderConfig
import com.dreamsoftware.rest.dto.OTPGenerationRequestDTO

interface OTPGenerator {

    fun generate(senderConfig: OtpSenderConfig, otpGenerationRequestDTO: OTPGenerationRequestDTO): OTPGenerated
}