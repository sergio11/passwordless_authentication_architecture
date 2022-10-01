package com.dreamsoftware.model.exception

abstract class OTPRepositoryException(message: String? = null, cause: Throwable? = null): Exception(message, cause)

class SaveOTPDataException(message: String? = null, cause: Throwable? = null): OTPRepositoryException(message, cause)

class OTPNotFoundException(message: String? = null, cause: Throwable? = null): OTPRepositoryException(message, cause)
