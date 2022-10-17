package com.dreamsoftware.model.exception

open class OTPRepositoryException(message: String? = null, cause: Throwable? = null): Exception(message, cause)
class OTPSaveDataException(message: String? = null, cause: Throwable? = null): OTPRepositoryException(message, cause)
class OTPNotFoundException(message: String? = null, cause: Throwable? = null): OTPRepositoryException(message, cause)
