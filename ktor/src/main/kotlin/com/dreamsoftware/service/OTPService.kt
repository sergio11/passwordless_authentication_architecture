package com.dreamsoftware.service

import com.dreamsoftware.model.exception.OTPSenderNotFoundException
import com.dreamsoftware.rest.dto.OTPGenerationRequestDTO
import com.dreamsoftware.rest.dto.OTPGenerationResultDTO
import com.dreamsoftware.rest.dto.OTPVerifyRequestDTO
import com.dreamsoftware.rest.dto.OTPVerifyResultDTO

interface OTPService {

    @Throws(OTPSenderNotFoundException::class)
    suspend fun generate(otpGenerationRequestDTO: OTPGenerationRequestDTO): OTPGenerationResultDTO

    suspend fun verify(otpVerifyRequestDTO: OTPVerifyRequestDTO): OTPVerifyResultDTO
}