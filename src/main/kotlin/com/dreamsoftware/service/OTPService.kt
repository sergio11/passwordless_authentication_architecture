package com.dreamsoftware.service

import com.dreamsoftware.model.exception.OTPSenderNotFoundException
import com.dreamsoftware.rest.dto.OTPGenerationRequestDTO
import com.dreamsoftware.rest.dto.OTPGenerationResultDTO
import com.dreamsoftware.rest.dto.OTPVerifyRequestDTO
import com.dreamsoftware.rest.dto.OTPVerifyResultDTO
import kotlin.jvm.Throws

interface OTPService {

    @Throws(OTPSenderNotFoundException::class)
    fun generate(otpGenerationRequestDTO: OTPGenerationRequestDTO): OTPGenerationResultDTO

    fun verify(otpVerifyRequestDTO: OTPVerifyRequestDTO): OTPVerifyResultDTO
}