package com.dreamsoftware.utils

import java.security.MessageDigest
import java.util.*

fun String.hashSha256andEncode() =
    Base64.getEncoder().encodeToString(
        MessageDigest.getInstance("SHA-256")
        .digest(toByteArray()))