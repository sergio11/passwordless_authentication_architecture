package com.dreamsoftware.service

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.exception.OTPSenderNotFoundException
import kotlin.jvm.Throws

interface OTPGenerator {

    @Throws(OTPSenderNotFoundException::class)
    fun generate(senderId: String, dest: String): OTPGenerated
}