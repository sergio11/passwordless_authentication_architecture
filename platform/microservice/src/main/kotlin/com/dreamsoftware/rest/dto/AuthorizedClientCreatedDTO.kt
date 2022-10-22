package com.dreamsoftware.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorizedClientCreatedDTO (
    @SerialName("id")
    val id: String
)