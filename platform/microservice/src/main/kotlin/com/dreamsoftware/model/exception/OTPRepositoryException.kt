package com.dreamsoftware.model.exception

open class OTPRepositoryException(message: String? = null, cause: Throwable? = null): Exception(message, cause)

class OTPSaveDataException(message: String? = null, cause: Throwable? = null): OTPRepositoryException(message, cause)
class OTPNotFoundException(message: String? = null, cause: Throwable? = null): OTPRepositoryException(message, cause)
class OTPSenderFailedException(message: String? = null, cause: Throwable? = null): Exception(message, cause)
class OTPSenderNotFoundException(message: String? = null, cause: Throwable? = null): Exception(message, cause)
class OTPDestinationIsBlockedException(message: String? = null, cause: Throwable? = null): Exception(message, cause)
class OTPMaxAttemptsAllowedReachedException(message: String? = null, cause: Throwable? = null): Exception(message, cause)
