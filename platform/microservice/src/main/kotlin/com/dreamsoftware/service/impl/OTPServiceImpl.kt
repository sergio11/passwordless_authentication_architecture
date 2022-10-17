package com.dreamsoftware.service.impl

import com.dreamsoftware.di.MAIL_NOTIFICATION_SENDER
import com.dreamsoftware.di.PUSH_NOTIFICATION_SENDER
import com.dreamsoftware.di.SMS_NOTIFICATION_SENDER
import com.dreamsoftware.model.MfaConfig
import com.dreamsoftware.model.OtpSenderConfig
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
    private val mfaConfig: MfaConfig
): OTPService, KoinComponent {

    override suspend fun generate(otpGenerationRequestDTO: OTPGenerationRequestDTO): OTPGenerationResultDTO = with(otpGenerationRequestDTO) {
        runCatching {
            val otpGenerated = otpRepository.findByDestination(destination)
            OTPGenerationResultDTO(operationId = otpGenerated.operationId)
        }.getOrElse {
            getOtpSenderConfig(type).let { otpSenderConfig ->
                otpGenerator.generate(otpSenderConfig, destination).also { otpGenerated ->
                    otpRepository.save(otpGenerated).also {
                        getOtpSender<OtpSenderConfig>(type).apply {
                            sendOTP(
                                otpSenderConfig,
                                otpGenerated.otp,
                                otpGenerated.destination,
                                properties
                            )
                        }
                    }
                }.let {
                    OTPGenerationResultDTO(operationId = it.operationId)
                }
            }
        }
    }

    override suspend fun verify(otpVerifyRequestDTO: OTPVerifyRequestDTO): OTPVerifyResultDTO = with(otpVerifyRequestDTO) {
        with(otpRepository) {
            OTPVerifyResultDTO(operationId, existsByOperationIdAndOtp(operationId, otp).also {
                deleteByOperationId(operationId)
            })
        }
    }

    private fun getOtpSenderConfig(type: OTPTypeEnum) = when(type) {
        OTPTypeEnum.SMS -> mfaConfig.smsSender
        OTPTypeEnum.PUSH -> mfaConfig.pushSender
        OTPTypeEnum.MAIL -> mfaConfig.mailSender
    }

    private fun <T: OtpSenderConfig> getOtpSender(type: OTPTypeEnum): OTPSender<T> =  when(type) {
        OTPTypeEnum.SMS -> get(named(SMS_NOTIFICATION_SENDER))
        OTPTypeEnum.PUSH -> get(named(PUSH_NOTIFICATION_SENDER))
        OTPTypeEnum.MAIL -> get(named(MAIL_NOTIFICATION_SENDER))
    }
}