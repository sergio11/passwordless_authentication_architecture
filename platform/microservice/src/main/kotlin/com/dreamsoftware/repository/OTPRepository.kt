package com.dreamsoftware.repository

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.exception.OTPNotFoundException
import com.dreamsoftware.model.exception.OTPRepositoryException
import com.dreamsoftware.model.exception.OTPSaveDataException

interface OTPRepository {

    @Throws(OTPSaveDataException::class)
    fun save(otpGenerated: OTPGenerated)

    @Throws(OTPNotFoundException::class)
    fun findByDestination(destination: String): OTPGenerated

    @Throws(OTPNotFoundException::class)
    fun findByOperationId(operationId: String): OTPGenerated

    fun existsByOperationIdAndOtp(operationId: String, otp: String): Boolean

    @Throws(OTPRepositoryException::class)
    fun deleteByOperationId(operationId: String)

}