package com.dreamsoftware.model.fcm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationRequestData(
    val data: Map<String, String>?,
    @SerialName("registration_ids")
    val registrationTokenList: List<String>?,
    val notification: NotificationData?,
    val priority: String
)
