package com.dreamsoftware.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateAuthorizedClientDTO (
    @SerialName("name")
    val name: String,
    @SerialName("password")
    val password: String
)