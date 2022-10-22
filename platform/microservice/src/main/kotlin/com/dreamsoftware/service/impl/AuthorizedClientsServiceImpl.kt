package com.dreamsoftware.service.impl

import com.dreamsoftware.repository.AuthorizedClientsRepository
import com.dreamsoftware.rest.dto.AuthorizedClientCreatedDTO
import com.dreamsoftware.rest.dto.CreateAuthorizedClientDTO
import com.dreamsoftware.rest.dto.DeleteAuthorizedClientDTO
import com.dreamsoftware.service.AuthorizedClientsService
import org.koin.core.component.KoinComponent

class AuthorizedClientsServiceImpl(
    private val authorizedClientsRepository: AuthorizedClientsRepository
): AuthorizedClientsService, KoinComponent {

    override fun create(authorizedClient: CreateAuthorizedClientDTO): AuthorizedClientCreatedDTO = with(authorizedClient) {
        AuthorizedClientCreatedDTO(id = authorizedClientsRepository.save(name, password))
    }

    override fun delete(authorizedClient: DeleteAuthorizedClientDTO) {
        authorizedClientsRepository.delete(id = authorizedClient.id)
    }

}