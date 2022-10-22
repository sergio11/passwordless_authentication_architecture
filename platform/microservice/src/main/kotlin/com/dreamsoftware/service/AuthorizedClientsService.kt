package com.dreamsoftware.service

import com.dreamsoftware.model.exception.AuthorizedClientsRemoveException
import com.dreamsoftware.model.exception.AuthorizedClientsSaveException
import com.dreamsoftware.rest.dto.AuthorizedClientCreatedDTO
import com.dreamsoftware.rest.dto.CreateAuthorizedClientDTO
import com.dreamsoftware.rest.dto.DeleteAuthorizedClientDTO

interface AuthorizedClientsService {

    @Throws(AuthorizedClientsSaveException::class)
    fun create(authorizedClient: CreateAuthorizedClientDTO): AuthorizedClientCreatedDTO

    @Throws(AuthorizedClientsRemoveException::class)
    fun delete(authorizedClient: DeleteAuthorizedClientDTO)
}