package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.UserRepository
import com.ndc.neostore.data.source.network.firebase.dto.UserDto
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        onSuccess: (UserDto) -> Unit,
        onFailure: (String) -> Unit,
    ) = userRepository.getUser(
        onSuccess = onSuccess,
        onFailure = onFailure
    )
}