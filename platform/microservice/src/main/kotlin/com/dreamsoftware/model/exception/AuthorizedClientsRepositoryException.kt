package com.dreamsoftware.model.exception

open class AuthorizedClientsRepositoryException(message: String? = null, cause: Throwable? = null): Exception(message, cause)

class AuthorizedClientsSaveException(message: String? = null, cause: Throwable? = null): AuthorizedClientsRepositoryException(message, cause)
class AuthorizedClientsRemoveException(message: String? = null, cause: Throwable? = null): AuthorizedClientsRepositoryException(message, cause)
class AuthorizedClientsCheckException(message: String? = null, cause: Throwable? = null): AuthorizedClientsRepositoryException(message, cause)
class UnauthorizedClientException(message: String? = null, cause: Throwable? = null): AuthorizedClientsRepositoryException(message, cause)
