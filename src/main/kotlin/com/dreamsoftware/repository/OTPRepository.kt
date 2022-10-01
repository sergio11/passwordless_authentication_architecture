package com.dreamsoftware.repository

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.exception.OTPNotFoundException
import com.dreamsoftware.model.exception.SaveOTPDataException
import kotlin.jvm.Throws

interface OTPRepository {

    @Throws(SaveOTPDataException::class)
    fun save(userId: String, otpGenerated: OTPGenerated)

    @Throws(OTPNotFoundException::class)
    fun findNotExpiredByUserId(userId: String): OTPGenerated

}