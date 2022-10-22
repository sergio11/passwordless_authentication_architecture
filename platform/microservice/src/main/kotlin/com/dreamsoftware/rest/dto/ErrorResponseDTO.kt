package com.dreamsoftware.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDTO(
    @SerialName("name")
    private val name: String,
    @SerialName("code")
    private val code: Int,
    @SerialName("message")
    private val message: String,
    @SerialName("details")
    private val details: String? = null
)
