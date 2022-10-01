package com.dreamsoftware.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OTPGenerationResultDTO(
    @SerialName("operation_id")
    val operationId: String
)