package com.dreamsoftware.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OTPGenerationRequestDTO(
    @SerialName("type")
    val type: OTPTypeEnum,
    @SerialName("user_id")
    val userId: String,
    @SerialName("destination")
    val destination: String,
    @SerialName("properties")
    val properties: Map<String, String>
)
