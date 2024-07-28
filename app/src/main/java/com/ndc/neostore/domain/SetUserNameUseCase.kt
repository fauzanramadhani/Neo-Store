package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.UserRepository
import javax.inject.Inject

class SetUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        name: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) = userRepository.setUserName(name, onSuccess, onFailure)
}