package com.dreamsoftware.service

import com.dreamsoftware.model.exception.OTPMaxAttemptsAllowedReachedException
import com.dreamsoftware.model.exception.OTPNotFoundException
import com.dreamsoftware.model.exception.OTPSenderFailedException
import com.dreamsoftware.rest.dto.*

interface OTPService {

    @Throws(OTPSenderFailedException::class)
    suspend fun generate(otpGenerationRequestDTO: OTPGenerationRequestDTO): OTPGenerationResultDTO

    @Throws(OTPSenderFailedException::class, OTPNotFoundException::class, OTPMaxAttemptsAllowedReachedException::class)
    suspend fun resend(otpResendRequestDTO: OTPResendRequestDTO): OTPGenerationResultDTO

    suspend fun verify(otpVerifyRequestDTO: OTPVerifyRequestDTO): OTPVerifyResultDTO
}