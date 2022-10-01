package com.dreamsoftware.service.impl

import com.dreamsoftware.model.MfaConfig
import com.dreamsoftware.model.exception.OTPSenderNotFoundException
import com.dreamsoftware.repository.OTPRepository
import com.dreamsoftware.rest.dto.OTPGenerationRequestDTO
import com.dreamsoftware.rest.dto.OTPGenerationResultDTO
import com.dreamsoftware.rest.dto.OTPVerifyRequestDTO
import com.dreamsoftware.rest.dto.OTPVerifyResultDTO
import com.dreamsoftware.service.OTPGenerator
import com.dreamsoftware.service.OTPSender
import com.dreamsoftware.service.OTPService
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class OTPServiceImpl(
    private val otpRepository: OTPRepository,
    private val otpGenerator: OTPGenerator,
    private val mfaConfig: MfaConfig
): OTPService, KoinComponent {

    override fun generate(otpGenerationRequestDTO: OTPGenerationRequestDTO): OTPGenerationResultDTO = with(otpGenerationRequestDTO) {
        runCatching {
            OTPGenerationResultDTO(operationId = otpRepository.findNotExpiredByUserId(userId).operationId.toString())
        }.getOrElse {
            mfaConfig.senders.find { it.id == type.name }?.let { otpSenderConfig ->
                otpGenerator.generate(otpSenderConfig, destination).also { otpGenerated ->
                    otpRepository.save(userId, otpGenerated).also {
                        get<OTPSender> { parametersOf(type) }.apply {
                            sendOTP(
                                otpSenderConfig,
                                otpGenerated.otp,
                                otpGenerated.destination,
                                properties
                            )
                        }
                    }
                }.let {
                    OTPGenerationResultDTO(operationId = it.operationId.toString())
                }
            } ?: throw OTPSenderNotFoundException()
        }
    }

    override fun verify(otpVerifyRequestDTO: OTPVerifyRequestDTO): OTPVerifyResultDTO {
        TODO("Not yet implemented")
    }
}