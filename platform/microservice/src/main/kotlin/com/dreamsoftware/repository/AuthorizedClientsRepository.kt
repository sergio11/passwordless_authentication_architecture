package com.dreamsoftware.repository

import com.dreamsoftware.model.exception.AuthorizedClientsRemoveException
import com.dreamsoftware.model.exception.AuthorizedClientsSaveException

interface AuthorizedClientsRepository {

    @Throws(AuthorizedClientsSaveException::class)
    fun save(name: String, password: String): String

    @Throws(AuthorizedClientsRemoveException::class)
    fun delete(id: String)

    fun exists(id: String): Boolean
}