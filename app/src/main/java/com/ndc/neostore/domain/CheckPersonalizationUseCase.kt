package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.UserRepository
import javax.inject.Inject

class CheckPersonalizationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        onSuccess: (Boolean) -> Unit,
        onFailure: (String) -> Unit
    ) = userRepository.checkPersonalization(onSuccess, onFailure)
}