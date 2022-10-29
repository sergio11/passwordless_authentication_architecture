package com.dreamsoftware.service.impl

import com.dreamsoftware.di.MAIL_NOTIFICATION_SENDER
import com.dreamsoftware.di.PUSH_NOTIFICATION_SENDER
import com.dreamsoftware.di.SMS_NOTIFICATION_SENDER
import com.dreamsoftware.model.SendersConfig
import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.OtpSenderConfig
import com.dreamsoftware.model.exception.OTPMaxAttemptsAllowedReachedException
import com.dreamsoftware.repository.OTPRepository
import com.dreamsoftware.rest.dto.*
import com.dreamsoftware.service.OTPGenerator
import com.dreamsoftware.service.OTPSender
import com.dreamsoftware.service.OTPService
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

class OTPServiceImpl(
    private val otpRepository: OTPRepository,
    private val otpGenerator: OTPGenerator,
    private val sendersConfig: SendersConfig
): OTPService, KoinComponent {

    private companion object {
        const val MAX_ATTEMPTS_ALLOWED = 3
    }

    override suspend fun generate(otpGenerationRequestDTO: OTPGenerationRequestDTO): OTPGenerationResultDTO = with(otpGenerationRequestDTO) {
        runCatching {
            with(otpRepository.findByDestination(destination)){
                OTPGenerationResultDTO(operationId = operationId)
            }
        }.getOrElse {
            getOtpSenderConfig(type).let { otpSenderConfig ->
                sendAndSaveOTP(otpSenderConfig, otpGenerator.generate(otpSenderConfig, otpGenerationRequestDTO))
            }
        }
    }

    override suspend fun resend(otpResendRequestDTO: OTPResendRequestDTO): OTPGenerationResultDTO = with(otpResendRequestDTO) {
        with(otpRepository) {
            findByOperationId(operationId).let { otpGenerated ->
                val currentAttempts = otpGenerated.attempts + 1
                if(currentAttempts > MAX_ATTEMPTS_ALLOWED) {
                    deleteByOperationId(otpGenerated.operationId)
                    throw OTPMaxAttemptsAllowedReachedException("No more attempts are allowed")
                } else {
                    sendAndSaveOTP(getOtpSenderConfig(otpGenerated.senderType), otpGenerated.copy(attempts = currentAttempts))
                }
            }
        }
    }

    override suspend fun verify(otpVerifyRequestDTO: OTPVerifyRequestDTO): OTPVerifyResultDTO = with(otpVerifyRequestDTO) {
        with(otpRepository) {
            OTPVerifyResultDTO(operationId, existsByOperationIdAndOtp(operationId, otp).also {exists ->
                deleteByOperationId(operationId)
                if(!exists) {

                }
            })
        }
    }

    /**
     * Private Methods
     */

    private fun getOtpSenderConfig(type: OTPTypeEnum) = when(type) {
        OTPTypeEnum.SMS -> sendersConfig.smsSender
        OTPTypeEnum.PUSH -> sendersConfig.pushSender
        OTPTypeEnum.MAIL -> sendersConfig.mailSender
    }

    private fun <T: OtpSenderConfig> getOtpSender(type: OTPTypeEnum): OTPSender<T> =  when(type) {
        OTPTypeEnum.SMS -> get(named(SMS_NOTIFICATION_SENDER))
        OTPTypeEnum.PUSH -> get(named(PUSH_NOTIFICATION_SENDER))
        OTPTypeEnum.MAIL -> get(named(MAIL_NOTIFICATION_SENDER))
    }

    private suspend fun sendAndSaveOTP(otpSenderConfig: OtpSenderConfig, otpGenerated: OTPGenerated): OTPGenerationResultDTO =
        with(otpGenerated) {
            getOtpSender<OtpSenderConfig>(senderType).apply {
                sendOTP(
                    otpSenderConfig,
                    otp,
                    destination,
                    properties
                )
            }
            otpRepository.save(this)
            OTPGenerationResultDTO(operationId = otpGenerated.operationId)
        }

}